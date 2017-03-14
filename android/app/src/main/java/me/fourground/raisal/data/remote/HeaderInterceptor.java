package me.fourground.raisal.data.remote;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import me.fourground.raisal.RaisalApplication;
import me.fourground.raisal.data.local.PreferencesHelper;
import me.fourground.raisal.injection.ApplicationContext;
import me.fourground.raisal.util.StringUtil;
import me.fourground.raisal.util.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class HeaderInterceptor implements Interceptor {

    @Inject
    PreferencesHelper mPreferencesHelper;
    @Inject
    @ApplicationContext
    Context mContext;

    public HeaderInterceptor(Context context) {
        RaisalApplication.get(context).getComponent().inject(this);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        String appId = null;
        String clientId = null;
        String authorization = null;

        Timber.tag("NetworkServiceFactory").d("mContext is null " + (mContext == null));

        appId = getDeviceUUID(mContext);
        clientId = mPreferencesHelper.getClientId();
        authorization = Util.buildAuthorization(mPreferencesHelper.getAccessToken());

        Timber.tag("NetworkServiceFactory").d("appId " + appId);
        Timber.tag("NetworkServiceFactory").d("clientId " + clientId);
        Timber.tag("NetworkServiceFactory").d("authorization " + authorization);

        Request.Builder builder = original.newBuilder();


        builder.header("Content-Type", "application/json")
                .header("User-Agent", "Android Tablet")
                .header("DEVICE-MODEL", Build.DEVICE)
                .header("OS", "A")
                .header("OS-VER", Build.VERSION.RELEASE)
                .header("MARKET", Build.BRAND)
                .method(original.method(), original.body());

        if (!StringUtil.isEmpty(appId)) {
            builder.header("APP-ID", appId);
        }

        if (!StringUtil.isEmpty(authorization)) {
            builder.header("Authorization", authorization);
        }

        if (!StringUtil.isEmpty(clientId)) {
            builder.header("CLIENT-ID", clientId);
        }

        Request request = builder.build();

        return chain.proceed(request);
    }

    /**
     * [주의!] 아래 예제는 샘플앱에서 사용되는 것으로 기기정보 일부가 포함될 수 있습니다. 실제 릴리즈 되는 앱에서 사용하기 위해서는 사용자로부터 개인정보 취급에 대한 동의를 받으셔야 합니다.
     * <p>
     * 한 사용자에게 여러 기기를 허용하기 위해 기기별 id가 필요하다.
     * ANDROID_ID가 기기마다 다른 값을 준다고 보장할 수 없어, 보완된 로직이 포함되어 있다.
     *
     * @return 기기의 unique id
     */
    private String getDeviceUUID(Context context) {
        String deviceUUID = mPreferencesHelper.getAppId();
        if (deviceUUID != null) {
            return deviceUUID;
        } else {
            UUID uuid = null;
            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            deviceUUID = uuid.toString();

            mPreferencesHelper.putAppId(deviceUUID);


            return deviceUUID;
        }
    }
}
