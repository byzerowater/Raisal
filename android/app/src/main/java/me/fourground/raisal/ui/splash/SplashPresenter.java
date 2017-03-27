package me.fourground.raisal.ui.splash;

import javax.inject.Inject;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.ui.base.BasePresenter;
import me.fourground.raisal.ui.base.Presenter;
import rx.Subscription;

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
}
