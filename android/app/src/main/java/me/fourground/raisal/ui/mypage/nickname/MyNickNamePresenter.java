package me.fourground.raisal.ui.mypage.nickname;

import android.content.Context;

import javax.inject.Inject;

import me.fourground.raisal.R;
import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.UpdateNickNameRequest;
import me.fourground.raisal.ui.base.BasePresenter;
import me.fourground.raisal.util.DialogFactory;
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
public class MyNickNamePresenter extends BasePresenter<MyNickNameMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MyNickNamePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void updateNickName(String nickName) {
        getMvpView().showProgress(true);
        mSubscription = mDataManager.updateNickName(
                new UpdateNickNameRequest(nickName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        getMvpView().onComplete();
                    }
                });

    }
}
