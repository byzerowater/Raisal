package me.fourground.raisal.data.remote;

import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * 4ground Ltd
 * byzerowater@gmail.com
 * Retrofit 인터페이스 정의 서버 쿼리
 */
public interface NetworkService {

    @POST("/api/auth/get")
    Observable<SignData> signIn(@Body SignInRequest loginRequest);


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
