package me.fourground.raisal.ui.content;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import me.fourground.raisal.ui.base.BasePresenter;
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
public class ContentPresenter extends BasePresenter<ContentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ContentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void login(FirebaseUser user, String chnCode) {
        getMvpView().showProgress(true);
        mSubscription = mDataManager.signIn(new SignInRequest(
                user.getUid(),
                user.getEmail(),
                chnCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SignData>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onNext(SignData signData) {

                    }
                });
    }
}
