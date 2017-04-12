package me.fourground.raisal.data.remote;

import me.fourground.raisal.data.model.AppListData;
import me.fourground.raisal.data.model.MyReviewListData;
import me.fourground.raisal.data.model.UpdateNickNameRequest;
import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.RegisterAppRequest;
import me.fourground.raisal.data.model.RegisterData;
import me.fourground.raisal.data.model.RegisterReviewRequest;
import me.fourground.raisal.data.model.ReviewListData;
import me.fourground.raisal.data.model.SignData;
import me.fourground.raisal.data.model.SignInRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
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
    Observable<RegisterData> registerReview(@Path("appId") String appId, @Body RegisterReviewRequest reviewRequest);

    /**
     * 닉네임 변경
     *
     * @param nickNameRequest
     * @return
     */
    @POST("/api/account/update")
    Observable<SignData> updateNickName(@Body UpdateNickNameRequest nickNameRequest);


    /**
     * 평가앱 상세 조회
     *
     * @return
     */
    @GET("/api/raisal/get/{appId}")
    Observable<ContentData> getContent(@Path("appId") String appId);

    /**
     * 평가앱 상세 리뷰 리스트 조회
     *
     * @return
     */
    @GET
    Observable<ReviewListData> getContentReviewList(@Url String url);

    /**
     * 평가앱 목록 조회
     *
     * @return
     */
    @GET
    Observable<AppListData> getAppList(@Url String url);

    /**
     * 내 등록 앱 목록 조회
     *
     * @return
     */
    @GET
    Observable<AppListData> getMyAppList(@Url String url);

    /**
     * 내 리뷰 목록 조회
     *
     * @return
     */
    @GET
    Observable<MyReviewListData> getMyReviewList(@Url String url);

}
