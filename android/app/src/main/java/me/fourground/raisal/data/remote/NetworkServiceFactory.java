package me.fourground.raisal.data.remote;

import android.content.Context;

import me.fourground.raisal.BuildConfig;
import me.fourground.raisal.injection.ApplicationContext;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * company Ltd
 * youngsoo.kim@yap.net
 * Retrofit 통신 환경 설정
 */
public class NetworkServiceFactory {


    public static NetworkService makeNetworkService(@ApplicationContext Context context) {
        OkHttpClient okHttpClient = makeOkHttpClient(context);
        return makeNetworkService(okHttpClient);
    }

    private static NetworkService makeNetworkService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit.create(NetworkService.class);
    }

    private static OkHttpClient makeOkHttpClient(@ApplicationContext Context context) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);


        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new HeaderInterceptor(context))
                .addInterceptor(new ErrorInterceptor(context))
                .build();
    }
}
