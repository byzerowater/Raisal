package me.fourground.litmus.data;

import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.data.model.ReviewData;

public class BusEvent {

    public static class AuthenticationError { }

    public static class SignInCompleted { }
    public static class SignOutCompleted { }


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

    public static class UpdateNicknameCompleted {
        String mNickname;

        public UpdateNicknameCompleted(String nickname) {

            mNickname = nickname;
        }

        public String getNickname() {
            return mNickname;
        }

    }

}
