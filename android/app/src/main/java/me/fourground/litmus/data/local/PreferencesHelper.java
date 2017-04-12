package me.fourground.litmus.data.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.fourground.litmus.data.model.SignData;
import me.fourground.litmus.injection.ApplicationContext;
import me.fourground.litmus.util.SharedPreferencesCache;

@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "raisal_pref_file";
    private static final String PREF_KEY_ACCESS_TOKEN = "pref_key_access_token";
    private static final String PREF_KEY_SIGN_DATA = "pref_key_sign_data";

    private final SharedPreferencesCache mPref;
    private final Gson mGson;


    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = new SharedPreferencesCache(context, PREF_FILE_NAME);
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
                .create();
    }

    public void clear() {
        mPref.clearAll();
    }

    public void putAccessToken(String accessToken) {
        mPref.put(PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    @Nullable
    public String getAccessToken() {
        return mPref.getString(PREF_KEY_ACCESS_TOKEN);
    }

    public void putSignData(SignData signData) {
        mPref.put(PREF_KEY_SIGN_DATA, mGson.toJson(signData));
    }

    public SignData getSignData() {
        String signData = mPref.getString(PREF_KEY_SIGN_DATA);
        if (signData == null) return null;
        return mGson.fromJson(signData, SignData.class);
    }
}
