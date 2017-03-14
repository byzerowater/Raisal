package me.fourground.raisal.injection.component;

import android.app.Application;
import android.content.Context;

import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.remote.ErrorInterceptor;
import me.fourground.raisal.data.remote.HeaderInterceptor;
import me.fourground.raisal.data.remote.NetworkService;
import me.fourground.raisal.injection.ApplicationContext;
import me.fourground.raisal.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(HeaderInterceptor headerInterceptor);
    void inject(ErrorInterceptor errorInterceptor);

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();

    NetworkService networkService();
}
