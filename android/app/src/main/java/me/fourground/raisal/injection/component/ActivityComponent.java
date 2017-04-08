package me.fourground.raisal.injection.component;

import dagger.Component;
import me.fourground.raisal.injection.PerActivity;
import me.fourground.raisal.injection.module.ActivityModule;
import me.fourground.raisal.ui.content.ContentActivity;
import me.fourground.raisal.ui.main.MainActivity;
import me.fourground.raisal.ui.mypage.MyPageActivity;
import me.fourground.raisal.ui.mypage.app.MyAppActivity;
import me.fourground.raisal.ui.mypage.review.MyReviewActivity;
import me.fourground.raisal.ui.signin.SignInActivity;
import me.fourground.raisal.ui.splash.SplashActivity;
import me.fourground.raisal.ui.write.app.WriteAppAppraisalActivity;
import me.fourground.raisal.ui.write.app.WriteAppAppraisalCompleteActivity;
import me.fourground.raisal.ui.write.review.WriteReviewActivity;
import me.fourground.raisal.ui.write.review.WriteReviewCompleteActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);

    void inject(SignInActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(WriteAppAppraisalActivity writeAppAppraisalActivity);

    void inject(WriteReviewActivity writeReviewActivity);

    void inject(MyPageActivity myPageActivity);

    void inject(WriteAppAppraisalCompleteActivity writeAppAppraisalCompleteActivity);

    void inject(MyReviewActivity myReviewActivity);

    void inject(MyAppActivity myAppActivity);

    void inject(ContentActivity contentActivity);

    void inject(WriteReviewCompleteActivity writeReviewCompleteActivity);
}

