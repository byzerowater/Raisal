package me.fourground.litmus.data.remote;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import java.io.IOException;

import javax.inject.Inject;

import me.fourground.litmus.LitmuslApplication;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.data.BusEvent;
import okhttp3.Interceptor;
import okhttp3.Response;

public class UnauthorisedInterceptor implements Interceptor {

    @Inject
    Bus eventBus;

    public UnauthorisedInterceptor(Context context) {
        LitmuslApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == Const.ERROR_UNAUTHORIZED) {
            new Handler(Looper.getMainLooper()).post(
                    () -> eventBus.post(new BusEvent.AuthenticationError()));
        }
        return response;
    }
}
