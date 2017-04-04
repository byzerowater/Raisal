package me.fourground.raisal.ui.main;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import me.fourground.raisal.BuildConfig;
import me.fourground.raisal.R;
import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.AppInfoData;
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
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

}
