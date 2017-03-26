package me.fourground.raisal.ui.mypage;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import me.fourground.raisal.ui.base.Presenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyPagePresenter implements Presenter<MyPageMvpView> {

    private final DataManager mDataManager;
    private MyPageMvpView mMvpView;
    private Subscription mSubscription;

    @Inject
    public MyPagePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MyPageMvpView mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void login(FirebaseUser user, String chnCode) {
        mMvpView.showProgress(true);
        mSubscription = mDataManager.signIn(new SignInRequest(
                user.getUid(),
                user.getEmail(),
                chnCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SignData>() {
                    @Override
                    public void onCompleted() {
                        mMvpView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        mMvpView.showProgress(false);
                    }

                    @Override
                    public void onNext(SignData signData) {

                    }
                });
    }
}
