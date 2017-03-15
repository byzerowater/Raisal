package me.fourground.raisal.injection.component;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import me.fourground.raisal.RaisalApplication;
import me.fourground.raisal.data.DataManager;
import me.fourground.raisal.data.remote.HeaderInterceptor;
import me.fourground.raisal.data.remote.NetworkService;
import me.fourground.raisal.data.remote.UnauthorisedInterceptor;
import me.fourground.raisal.injection.ApplicationContext;
import me.fourground.raisal.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(RaisalApplication raisalApplication);

    void inject(HeaderInterceptor headerInterceptor);

    void inject(UnauthorisedInterceptor unauthorisedInterceptor);

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();

    NetworkService networkService();

    Bus eventBus();
}
