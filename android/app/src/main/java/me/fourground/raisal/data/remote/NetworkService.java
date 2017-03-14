package me.fourground.raisal.data.remote;

import me.fourground.raisal.data.model.AccessTokenResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * company Ltd
 * youngsoo.kim@yap.net
 * Retrofit 인터페이스 정의 서버 쿼리
 */
public interface NetworkService {

    @GET("/api/anonymous/client/token/insert")
    Observable<AccessTokenResponse> getAccessToken();


//    /**
//     * Retrieve a list of shots
//     */
//    @GET("shots")
//    Single<List<Shot>> getShots(@Query("access_token") String accessToken,
//                                @Query("per_page") int perPage,
//                                @Query("page") int page);
//
//    /**
//     * Retrieve a list of comments for a given shot
//     */
//    @GET("shots/{shot_id}/comments")
//    Single<List<Comment>> getComments(@Path("shot_id") int shotId,
//                                      @Query("access_token") String accessToken,
//                                      @Query("per_page") int perPage,
//                                      @Query("page") int page);

}
