package me.fourground.litmus.ui.signin;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import me.fourground.litmus.R;
import me.fourground.litmus.data.DataManager;
import me.fourground.litmus.data.model.SignData;
import me.fourground.litmus.data.model.SignInRequest;
import me.fourground.litmus.ui.base.BasePresenter;
import me.fourground.litmus.util.DialogFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

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
        mSubscription = mDataManager.signIn(new SignInRequest(
                user.getUid(),
                user.getEmail(),
                channelCode))
                .subscribeOn(Schedulers.io())
                .retryWhen(err ->
                        err.observeOn(AndroidSchedulers.mainThread())
                                .flatMap(e -> {
                                    PublishSubject<Integer> choice = PublishSubject.create();
                                    Context context = (Context) getMvpView();
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SignData>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgress(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgress(false);
                        getMvpView().onError();
                    }

                    @Override
                    public void onNext(SignData signData) {
                        getMvpView().onSignIn();
                    }
                });

    }
}
