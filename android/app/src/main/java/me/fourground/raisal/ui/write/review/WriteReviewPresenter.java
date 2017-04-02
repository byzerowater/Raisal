package me.fourground.raisal.ui.write.review;

import android.content.Context;

import javax.inject.Inject;

import me.fourground.raisal.R;
import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.RegisterData;
import me.fourground.raisal.data.model.RegisterReviewRequest;
import me.fourground.raisal.ui.base.BasePresenter;
import me.fourground.raisal.util.DialogFactory;
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
public class WriteReviewPresenter extends BasePresenter<WriteReviewMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public WriteReviewPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
      super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void registerApp(String appId, RegisterReviewRequest registerReviewRequest) {
        getMvpView().showProgress(true);
        mSubscription = mDataManager.registerApp(appId, registerReviewRequest)
                .retryWhen(err ->
                        err.flatMap(e -> {
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
                .subscribeOn(Schedulers.io())
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
