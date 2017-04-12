package me.fourground.raisal.ui.mypage.review;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.List;

import javax.inject.Inject;

import me.fourground.raisal.BuildConfig;
import me.fourground.raisal.R;
import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.MyReviewData;
import me.fourground.raisal.ui.base.BasePresenter;
import me.fourground.raisal.util.DialogFactory;
import me.fourground.raisal.util.StringUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyReviewPresenter extends BasePresenter<MyReviewMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private String mNextUrl = BuildConfig.MY_APP_REVIEW_LIST_URL;

    @Inject
    public MyReviewPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getMyReviewList(boolean isShowProgress) {
        if (!StringUtil.isEmpty(mNextUrl)) {
            if (isShowProgress) {
                getMvpView().showProgress(true);
            }
            mSubscription = mDataManager.getMyReviewList(mNextUrl)
                    .retryWhen(err ->
                            err.observeOn(AndroidSchedulers.mainThread())
                                    .flatMap(e -> {
                                        PublishSubject<Integer> choice = PublishSubject.create();
                                        Context context = null;
                                        if (getMvpView() instanceof Fragment) {
                                            context = ((Fragment) getMvpView()).getActivity();
                                        } else {
                                            context = (Context) getMvpView();
                                        }
                                        DialogFactory.createDialog(context,
                                                context.getString(R.string.text_network_error),
                                                context.getString(R.string.action_close),
                                                context.getString(R.string.action_retry_connect),
                                                (dialog, which) -> {
                                                    choice.onError(e);
                                                },
                                                (dialog, which) -> {
                                                    choice.onNext(1);
                                                }).show();

                                        return choice;
                                    })
                    )
                    .map(appListData -> {
                        mNextUrl = appListData.getLinks().getNext();
                        return appListData.getData();
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<MyReviewData>>() {
                                   @Override
                                   public void onCompleted() {
                                       getMvpView().showProgress(false);
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       Timber.e(e);
                                       getMvpView().onError();
                                       getMvpView().showProgress(false);
                                   }

                                   @Override
                                   public void onNext(List<MyReviewData> datas) {
                                       getMvpView().onReviewList(datas);
                                   }
                               }
                    );
        }
    }

    public String getNextUrl() {
        return mNextUrl;
    }
}
