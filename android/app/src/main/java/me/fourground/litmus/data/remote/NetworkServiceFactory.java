package me.fourground.litmus.data.remote;

import android.content.Context;

import com.google.gson.Gson;

import me.fourground.litmus.BuildConfig;
import me.fourground.litmus.injection.ApplicationContext;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * 4ground Ltd
 * byzerowater@gmail.com
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
                .addInterceptor(new UnauthorisedInterceptor(context))
                .addInterceptor(new HeaderInterceptor(context))
                .build();
    }
}
