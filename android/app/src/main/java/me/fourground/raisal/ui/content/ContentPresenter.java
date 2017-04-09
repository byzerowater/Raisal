package me.fourground.raisal.ui.content;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import me.fourground.raisal.BuildConfig;
import me.fourground.raisal.R;
import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.BasePresenter;
import me.fourground.raisal.util.DialogFactory;
import rx.Observable;
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
public class ContentPresenter extends BasePresenter<ContentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private String mNextUrl = BuildConfig.APP_REVIEW_LIST_URL;

    @Inject
    public ContentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getContent(String appId) {
        getMvpView().showProgress(true);

        mNextUrl = mNextUrl.replace("{appId}", appId);

        Observable<ContentData> content = mDataManager.getContent(appId);
        Observable<List<ReviewData>> contentReviewList = mDataManager.getContentReviewList(mNextUrl)
                .map(reviewListData -> {
                    mNextUrl = reviewListData.getLinks().getNext();
                    return reviewListData.getData();
                });

        mSubscription = Observable.merge(content, contentReviewList)
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onNext(Object o) {
                        if (o instanceof ContentData) {
                            getMvpView().onContent((ContentData) o);
                        } else {
                            getMvpView().onReviewList((List<ReviewData>) o);
                        }
                    }
                });
    }

    public void getReviewList() {
        mSubscription = mDataManager.getContentReviewList(mNextUrl)
                .map(reviewListData -> {
                    mNextUrl = reviewListData.getLinks().getNext();
                    return reviewListData.getData();
                }).retryWhen(err ->
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ReviewData>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onNext(List<ReviewData> reviewDatas) {
                        getMvpView().onReviewList(reviewDatas);
                    }
                });
    }
}
