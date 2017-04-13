package me.fourground.litmus.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.fourground.litmus.common.Const;
import me.fourground.litmus.data.local.PreferencesHelper;
import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.data.model.AppListData;
import me.fourground.litmus.data.model.ContentData;
import me.fourground.litmus.data.model.MyReviewListData;
import me.fourground.litmus.data.model.RegisterAppRequest;
import me.fourground.litmus.data.model.RegisterData;
import me.fourground.litmus.data.model.RegisterReviewRequest;
import me.fourground.litmus.data.model.ReviewData;
import me.fourground.litmus.data.model.ReviewListData;
import me.fourground.litmus.data.model.SignData;
import me.fourground.litmus.data.model.SignInRequest;
import me.fourground.litmus.data.model.UpdateNickNameRequest;
import me.fourground.litmus.data.remote.EventPosterHelper;
import me.fourground.litmus.data.remote.NetworkService;
import me.fourground.litmus.util.DateUtil;
import me.fourground.litmus.util.StringUtil;
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
                .doOnNext(signData -> {
                    mPreferencesHelper.putAccessToken(signData.getAuthKey());
                    mPreferencesHelper.putSignData(signData);
                    mEventPoster.postEventSafely(new BusEvent.SigninCompleted());
                });
    }

    public Observable<String> signout() {
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

    public Observable<AppListData> getMyAppList(String url) {
        return mNetworkService.getMyAppList(url);
    }

    public Observable<MyReviewListData> getMyReviewList(String url) {
        return mNetworkService.getMyReviewList(url);
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

                    Calendar currentCalendar = Calendar.getInstance();
                    String startDate = DateUtil.getDateFormat(currentCalendar.getTime(), Const.DATE_FORMAT_SERVER);
                    currentCalendar.add(Calendar.DATE, StringUtil.getInt(registAppRequest.getReqTerm()));
                    String endDate = DateUtil.getDateFormat(currentCalendar.getTime(), Const.DATE_FORMAT_SERVER);
                    String uid = null;
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        uid = currentUser.getUid();
                    }

                    AppInfoData data = new AppInfoData(
                            registerData.getAppId(),
                            registAppRequest.getAppName(),
                            Const.APPRAISAL_TYPE_ACTIVIE,
                            startDate,
                            endDate,
                            Const.STORE_TYPE_ADR,
                            0,
                            0,
                            uid
                    );
                    mEventPoster.postEventSafely(new BusEvent.RegisterAppCompleted(data));
                });
    }

    public Observable<RegisterData> registerReview(String appId, RegisterReviewRequest registerReviewRequest) {
        return mNetworkService.registerReview(appId, registerReviewRequest)
                .doOnNext(registerData -> {
                    ReviewData data = new ReviewData();
                    data.setAppId(appId);
                    data.setAppComment(registerReviewRequest.getComment());
                    data.setAppElement(registerReviewRequest.getAppElement());
                    data.setUserName(mPreferencesHelper.getSignData().getNickName());
                    mEventPoster.postEventSafely(new BusEvent.RegisterReviewCompleted(data));
                });
    }

    public Observable<SignData> updateNickName(UpdateNickNameRequest nickNameRequest) {
        return mNetworkService.updateNickName(nickNameRequest)
                .doOnNext(signData -> {
                    mPreferencesHelper.putSignData(signData);
                    mEventPoster.postEventSafely(new BusEvent.UpdateNicknameCompleted(nickNameRequest.getUserNm()));
                });
    }

    public String getNickName() {
        return mPreferencesHelper.getSignData().getNickName();
    }

    private String removeAccessToken() {
        String accessToken = mPreferencesHelper.getAccessToken();
        mPreferencesHelper.putAccessToken(null);
        FirebaseAuth.getInstance().signOut();
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