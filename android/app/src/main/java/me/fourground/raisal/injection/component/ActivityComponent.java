package me.fourground.raisal.injection.component;

import me.fourground.raisal.injection.PerActivity;
import me.fourground.raisal.injection.module.ActivityModule;
import me.fourground.raisal.ui.login.LoginActivity;
import me.fourground.raisal.ui.splash.SplashActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);
    void inject(LoginActivity loginActivity);
}

