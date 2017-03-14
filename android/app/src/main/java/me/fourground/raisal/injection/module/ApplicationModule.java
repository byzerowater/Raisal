package me.fourground.raisal.injection.module;

import android.app.Application;
import android.content.Context;

import me.fourground.raisal.data.remote.NetworkService;
import com.squareup.otto.Bus;
import me.fourground.raisal.data.remote.NetworkServiceFactory;
import me.fourground.raisal.injection.ApplicationContext;
import me.fourground.raisal.util.ToastManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }



    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService() {
        return NetworkServiceFactory.makeNetworkService(mApplication);
    }

    @Provides
    ToastManager provideToastManager() {
        return new ToastManager(mApplication);
    }

}