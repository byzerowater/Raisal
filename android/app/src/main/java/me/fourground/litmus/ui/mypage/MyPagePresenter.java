package me.fourground.litmus.ui.mypage;

import javax.inject.Inject;

import me.fourground.litmus.data.DataManager;
import me.fourground.litmus.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyPagePresenter extends BasePresenter<MyPageMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MyPagePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void signOut() {
        getMvpView().showProgress(true);
        mSubscription = mDataManager.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onNext(String s) {
                        getMvpView().onSignOut();
                    }
                });
    }

    public String getNickName() {
        return mDataManager.getNickName();
    }
}
