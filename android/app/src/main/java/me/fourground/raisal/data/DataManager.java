package me.fourground.raisal.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.fourground.raisal.common.Const;
import me.fourground.raisal.data.local.PreferencesHelper;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.AppListData;
import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.RegisterAppRequest;
import me.fourground.raisal.data.model.RegisterData;
import me.fourground.raisal.data.model.RegisterReviewRequest;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.data.model.ReviewListData;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import me.fourground.raisal.data.remote.EventPosterHelper;
import me.fourground.raisal.data.remote.NetworkService;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 데이터 매니져 데이터 접근 및 관리
 */
@Singleton
public class DataManager {

    private final NetworkService mNetworkService;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(NetworkService networkService,
                       PreferencesHelper preferencesHelper,
                       EventPosterHelper eventPoster) {
        mNetworkService = networkService;
        mPreferencesHelper = preferencesHelper;
        mEventPoster = eventPoster;
    }

    public Observable<SignData> signIn(SignInRequest signInRequest) {
        return mNetworkService.signIn(signInRequest)
                .map(signData -> {
                    mPreferencesHelper.putAccessToken(signData.getAuthKey());
                    mPreferencesHelper.putSignData(signData);
                    return signData;
                });
    }

    public Observable<String> signOut() {
        return Observable.create(subscriber -> {
                    try {
                        subscriber.onNext(removeAccessToken());
                        subscriber.onCompleted();
                    } catch (Throwable error) {
                        subscriber.onError(error);
                    }
                }
        );
    }

    public Observable<AppListData> getAppList(String url) {
        return mNetworkService.getAppList(url);
    }

    public Observable<ContentData> getContent(String appId) {
        return mNetworkService.getContent(appId);
    }

    public Observable<ReviewListData> getContentReviewList(String url) {
        return mNetworkService.getContentReviewList(url);
    }

    public Observable<RegisterData> registerApp(RegisterAppRequest registAppRequest) {
        return mNetworkService.registerApp(registAppRequest)
                .doOnNext(registerData -> {
                    AppInfoData data = new AppInfoData(
                            registerData.getAppId(),
                            registAppRequest.getAppName(),
                            Const.APPRAISAL_TYPE_ACTIVIE,
                            "start",
                            "end",
                            Const.STORE_TYPE_ADR,
                            0,
                            0);
                    postEventSafelyAction(new BusEvent.RegisterAppCompleted(data));
                });
    }

    public Observable<RegisterData> registerApp(String appId, RegisterReviewRequest registerReviewRequest) {
        return mNetworkService.registerApp(appId, registerReviewRequest)
                .doOnNext(registerData ->  {
                    ReviewData data = new ReviewData();
                    data.setAppComment(registerReviewRequest.getComment());
                    data.setAppElement(registerReviewRequest.getAppElement());
                    data.setTargetOsCode(Const.STORE_TYPE_ADR);
                    data.setUserName(mPreferencesHelper.getSignData().getNickName());
                    postEventSafelyAction(new BusEvent.RegisterReviewCompleted(data));
                });
    }

    private String removeAccessToken() {
        String accessToken = mPreferencesHelper.getAccessToken();
        mPreferencesHelper.putAccessToken(null);
        return accessToken;
    }


//    public Single<List<Shot>> getShots(int perPage, int page) {
//        return mEnvironmentService.getShots(BuildConfig.DRIBBBLE_ACCESS_TOKEN, perPage, page);
//    }
//
//    public Single<List<Comment>> getComments(int id, int perPage, int page) {
//        return mEnvironmentService.getComments(id, BuildConfig.DRIBBBLE_ACCESS_TOKEN, perPage, page);
//    }

    //  Helper method to post events from doOnCompleted.
    private Action0 postEventSafelyAction(final Object event) {
        return new Action0() {
            @Override
            public void call() {
                mEventPoster.postEventSafely(event);
            }
        };
    }
}