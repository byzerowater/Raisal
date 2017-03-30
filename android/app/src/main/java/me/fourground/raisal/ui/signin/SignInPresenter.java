package me.fourground.raisal.ui.signin;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import me.fourground.raisal.ui.base.BasePresenter;
import me.fourground.raisal.util.RetryNetworkSubscriber;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SignInPresenter extends BasePresenter<SignInMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SignInPresenter(DataManager dataManager) {
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
}
