package me.fourground.litmus.ui.write.app;

import android.content.Context;

import javax.inject.Inject;

import me.fourground.litmus.R;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.data.DataManager;
import me.fourground.litmus.data.model.RegisterAppRequest;
import me.fourground.litmus.data.model.RegisterData;
import me.fourground.litmus.ui.base.BasePresenter;
import me.fourground.litmus.util.DialogFactory;
import retrofit2.HttpException;
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
public class WriteAppAppraisalPresenter extends BasePresenter<WriteAppAppraisalMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public WriteAppAppraisalPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void registerApp(RegisterAppRequest registerAppRequest) {
        getMvpView().showProgress(true);
        mSubscription = mDataManager.registerApp(registerAppRequest)
                .subscribeOn(Schedulers.io())
                .retryWhen(err ->
                        err.observeOn(AndroidSchedulers.mainThread())
                                .flatMap(e -> {
                                    int code = -1;
                                    PublishSubject<Integer> choice = PublishSubject.create();
                                    if (e instanceof HttpException) {
                                        code = ((HttpException) e).code();
                                    }
                                    Context context = (Context) getMvpView();

                                    if (code == Const.ERROR_CREATED) {
                                        DialogFactory.createDialog(context,
                                                context.getString(R.string.text_network_error_create),
                                                context.getString(R.string.action_close),
                                                (dialog, which) -> {
                                                    choice.onError(e);
                                                }).show();
                                    } else {

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
                                    }

                                    return choice;
                                })
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterData>() {
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
                    public void onNext(RegisterData registerData) {
                        Timber.d(registerData.toString());
                        getMvpView().onRegister();
                    }
                });
    }
}
