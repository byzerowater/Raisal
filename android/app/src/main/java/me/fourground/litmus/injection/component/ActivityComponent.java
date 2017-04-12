package me.fourground.litmus.injection.component;

import dagger.Component;
import me.fourground.litmus.injection.PerActivity;
import me.fourground.litmus.injection.module.ActivityModule;
import me.fourground.litmus.ui.content.ContentActivity;
import me.fourground.litmus.ui.main.MainActivity;
import me.fourground.litmus.ui.mypage.MyPageFragment;
import me.fourground.litmus.ui.mypage.app.MyAppActivity;
import me.fourground.litmus.ui.mypage.nickname.MyNickNameActivity;
import me.fourground.litmus.ui.mypage.review.MyReviewActivity;
import me.fourground.litmus.ui.signin.SignInActivity;
import me.fourground.litmus.ui.splash.SplashActivity;
import me.fourground.litmus.ui.write.app.WriteAppAppraisalActivity;
import me.fourground.litmus.ui.write.app.WriteAppAppraisalCompleteActivity;
import me.fourground.litmus.ui.write.review.WriteReviewActivity;
import me.fourground.litmus.ui.write.review.WriteReviewCompleteActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);

    void inject(SignInActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(WriteAppAppraisalActivity writeAppAppraisalActivity);

    void inject(WriteReviewActivity writeReviewActivity);

    void inject(MyPageFragment myPageActivity);

    void inject(WriteAppAppraisalCompleteActivity writeAppAppraisalCompleteActivity);

    void inject(MyReviewActivity myReviewActivity);

    void inject(ContentActivity contentActivity);

    void inject(WriteReviewCompleteActivity writeReviewCompleteActivity);

    void inject(MyAppActivity myAppActivity);

    void inject(MyNickNameActivity myNickNameActivity);
}

