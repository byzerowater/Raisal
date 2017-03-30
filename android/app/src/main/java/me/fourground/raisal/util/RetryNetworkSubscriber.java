package me.fourground.raisal.util;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.MvpView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2016-06-17.
 * 4ground Ltd
 * byzerowater@gmail.com
 * RxJava 통신 실패시 재시도 Subscriber
 */
public abstract class RetryNetworkSubscriber<T> extends Subscriber<T> {
    /**
     * MvpView
     */
    private MvpView mMvpView;
    /**
     * 통신 Observable
     */
    private Observable mObservable;
    /**
     * 응답 Subscriber
     */
    private Subscriber mSubscriber;

    /**
     * 연결 실패 다이얼로그
     */
    private Dialog mConnectDialog;

    /**
     * RxJava 통신 실패시 재시도 Subscriber
     *
     * @param mvpView    MvpView
     * @param observable 통신 Observable
     */
    public RetryNetworkSubscriber(MvpView mvpView, Observable observable) {
        mMvpView = mvpView;
        mObservable = observable;
    }

    @Override
    public void onCompleted() {
        if (mMvpView != null) {
            mMvpView.showProgress(false);
        }
    }

    @Override
    public void onError(Throwable e) {
        Timber.i("onError %s " + e.getLocalizedMessage());
        if (mMvpView != null) {
            mMvpView.showProgress(false);
            e.printStackTrace();


            if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
                Activity activity = null;

                if (mMvpView instanceof Activity) {
                    activity = (Activity) mMvpView;
                } else if (mMvpView instanceof Fragment) {
                    activity = ((Fragment) mMvpView).getActivity();
                }

                if (activity != null) {
                    if (mConnectDialog == null) {
                        mConnectDialog = DialogFactory.createDialog(activity,
                                activity.getString(R.string.text_network_error),
                                activity.getString(R.string.action_close),
                                activity.getString(R.string.action_retry_connect),
                                (dialog, which) -> {
                                    mMvpView.onError();
                                },
                                (dialog, which) -> {

                                    mMvpView.showProgress(true);
                                    if (mSubscriber != null) {
                                        mSubscriber.unsubscribe();
                                    }

                                    mSubscriber = new RetryNetworkSubscriber<T>(mMvpView, mObservable) {
                                        @Override
                                        public void onNext(T t) {
                                            RetryNetworkSubscriber.this.onNext(t);
                                        }
                                    };

                                    mObservable.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(mSubscriber);
                                });
                    }

                    if (!mConnectDialog.isShowing()) {
                        mConnectDialog.show();
                    }
                }

            } else {
                mMvpView.onError();
            }
        }
    }
}
