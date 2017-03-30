package me.fourground.raisal.ui.splash;

import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import me.fourground.raisal.ui.base.BasePresenter;
import me.fourground.raisal.util.RetryNetworkSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SplashPresenter extends BasePresenter<SplashMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void signIn(FirebaseUser user, String channelCode) {
        getMvpView().showProgress(true);
        Observable<SignData> signDataObservable = mDataManager.signIn(new SignInRequest(
                user.getUid(),
                user.getEmail(),
                channelCode));
        mSubscription = signDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetryNetworkSubscriber<SignData>(getMvpView(), signDataObservable) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        getMvpView().onSignIn();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(SignData signData) {
                    }
                });

    }

    /**
     * 스플레쉬 딜레이
     */
    public void delaySplash() {
        mSubscription = Observable.timer(2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                getMvpView().onGoMain();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {

            }
        });
    }
}
