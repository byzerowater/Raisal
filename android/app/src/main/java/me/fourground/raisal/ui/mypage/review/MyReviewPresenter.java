package me.fourground.raisal.ui.mypage.review;

import javax.inject.Inject;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.ui.base.BasePresenter;
import rx.Subscription;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyReviewPresenter extends BasePresenter<MyReviewMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MyReviewPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}
