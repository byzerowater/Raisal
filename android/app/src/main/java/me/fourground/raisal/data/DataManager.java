package me.fourground.raisal.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.fourground.raisal.data.local.PreferencesHelper;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import me.fourground.raisal.data.remote.EventPosterHelper;
import me.fourground.raisal.data.remote.NetworkService;
import rx.Observable;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * company Ltd
 * youngsoo.kim@yap.net
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
}