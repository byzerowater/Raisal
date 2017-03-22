package me.fourground.raisal.ui.splash;

import javax.inject.Inject;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.ui.base.Presenter;
import rx.Subscription;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SplashPresenter implements Presenter<SplashMvpView> {

    private final DataManager mDataManager;
    private SplashMvpView mMvpView;
    private Subscription mSubscription;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SplashMvpView mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}
