package me.fourground.raisal.injection.component;

import dagger.Component;
import me.fourground.raisal.injection.PerActivity;
import me.fourground.raisal.injection.module.ActivityModule;
import me.fourground.raisal.ui.main.MainActivity;
import me.fourground.raisal.ui.signin.SignInActivity;
import me.fourground.raisal.ui.splash.SplashActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);

    void inject(SignInActivity loginActivity);

    void inject(MainActivity mainActivity);
}

