package me.fourground.raisal.data;

import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.ReviewData;

public class BusEvent {

    public static class AuthenticationError { }

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
