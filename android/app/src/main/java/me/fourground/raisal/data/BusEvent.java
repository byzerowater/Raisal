package me.fourground.raisal.data;

import me.fourground.raisal.data.model.AppInfoData;

public class BusEvent {

    public static class AuthenticationError { }

    public static class RegisterCompleted {
        AppInfoData mAppInfoData;

        public RegisterCompleted(AppInfoData appInfoData) {
            mAppInfoData = appInfoData;
        }

        public AppInfoData getAppInfoData() {
            return mAppInfoData;
        }
    }

    public static class UserSignedOut { }
}
