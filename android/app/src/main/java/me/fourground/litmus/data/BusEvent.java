package me.fourground.litmus.data;

import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.data.model.ReviewData;

public class BusEvent {

    public static class AuthenticationError { }

    public static class SigninCompleted { }

    public static class RegisterAppCompleted {
        AppInfoData mAppInfoData;

        public RegisterAppCompleted(AppInfoData appInfoData) {
            mAppInfoData = appInfoData;
        }

        public AppInfoData getAppInfoData() {
            return mAppInfoData;
        }
    }

    public static class RegisterReviewCompleted {
        ReviewData mReviewData;

        public RegisterReviewCompleted(ReviewData reviewData) {
            mReviewData = reviewData;
        }

        public ReviewData getReviewData() {
            return mReviewData;
        }
    }

}
