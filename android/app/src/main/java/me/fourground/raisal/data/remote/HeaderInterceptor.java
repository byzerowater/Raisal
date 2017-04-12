package me.fourground.raisal.data.remote;

import android.content.Context;
import android.os.Build;

import java.io.IOException;

import javax.inject.Inject;

import me.fourground.raisal.RaisalApplication;
import me.fourground.raisal.data.local.PreferencesHelper;
import me.fourground.raisal.util.StringUtil;
import me.fourground.raisal.util.Util;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class HeaderInterceptor implements Interceptor {

    @Inject
    PreferencesHelper mPreferencesHelper;

    public HeaderInterceptor(Context context) {
        RaisalApplication.get(context).getComponent().inject(this);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        String authorization = Util.buildAuthorization(mPreferencesHelper.getAccessToken());

        Timber.tag("NetworkServiceFactory").d("authorization " + authorization);

        Request.Builder builder = original.newBuilder();


        builder.header("Content-Type", "application/json")
                .header("User-Agent", "Android LITMUS")
                .header("DEVICE-MODEL", Build.DEVICE)
                .header("OS", "A")
                .header("OS-VER", Build.VERSION.RELEASE)
                .header("MARKET", Build.BRAND)
                .method(original.method(), original.body());

        if (!StringUtil.isEmpty(authorization)) {
            builder.header("Authorization", authorization);
        }

        Request request = builder.build();

        return chain.proceed(request);
    }
}
