package me.fourground.raisal.data.local;

import android.content.Context;
import android.support.annotation.Nullable;

import me.fourground.raisal.injection.ApplicationContext;
import me.fourground.raisal.util.SharedPreferencesCache;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "raisal_pref_file";
    private static final String PREF_KEY_ACCESS_TOKEN = "pref_key_access_token";

    private final SharedPreferencesCache mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = new SharedPreferencesCache(context, PREF_FILE_NAME);
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
}
