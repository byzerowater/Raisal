package me.fourground.raisal.data.remote;

import java.util.List;

import me.fourground.raisal.data.model.RegisterAppRequest;
import me.fourground.raisal.data.model.RegisterData;
import me.fourground.raisal.data.model.RegisterReviewRequest;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * 4ground Ltd
 * byzerowater@gmail.com
 * Retrofit 인터페이스 정의 서버 쿼리
 */
public interface NetworkService {

    /**
     * 로그인
     *
     * @param loginRequest
     * @return
     */
    @POST("/api/auth/get")
    Observable<SignData> signIn(@Body SignInRequest loginRequest);

    /**
     * 앱평가 요청 등록
     *
     * @param registAppRequest
     * @return
     */
    @POST("/api/raisal/regist")
    Observable<RegisterData> registerApp(@Body RegisterAppRequest registAppRequest);

    /**
     * 앱평가 리뷰 등록
     *
     * @param reviewRequest
     * @return
     */
    @POST("/api/raisal/vote/{appId}")
    Observable<RegisterData> registerApp(@Path("appId") String appId, @Body RegisterReviewRequest reviewRequest);


    /**
     * 평가앱 목록 조회
     *
     * @return
     */
    @GET("/api/raisal/select")
    Observable<List<ReviewData>> getAppList();

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
