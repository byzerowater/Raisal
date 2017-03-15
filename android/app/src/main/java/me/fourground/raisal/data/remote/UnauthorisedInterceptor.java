package me.fourground.raisal.data.remote;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import java.io.IOException;

import javax.inject.Inject;

import me.fourground.raisal.RaisalApplication;
import me.fourground.raisal.data.BusEvent;
import okhttp3.Interceptor;
import okhttp3.Response;

public class UnauthorisedInterceptor implements Interceptor {

    @Inject
    Bus eventBus;

    public UnauthorisedInterceptor(Context context) {
        RaisalApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == 401) {
            new Handler(Looper.getMainLooper()).post(
                    () -> eventBus.post(new BusEvent.AuthenticationError()));
        }
        return response;
    }
}
