package me.fourground.litmus.injection.component;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import me.fourground.litmus.LitmuslApplication;
import me.fourground.litmus.data.DataManager;
import me.fourground.litmus.data.remote.HeaderInterceptor;
import me.fourground.litmus.data.remote.NetworkService;
import me.fourground.litmus.data.remote.UnauthorisedInterceptor;
import me.fourground.litmus.injection.ApplicationContext;
import me.fourground.litmus.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(LitmuslApplication litmuslApplication);

    void inject(HeaderInterceptor headerInterceptor);

    void inject(UnauthorisedInterceptor unauthorisedInterceptor);

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();

    NetworkService networkService();

    Bus eventBus();
}
