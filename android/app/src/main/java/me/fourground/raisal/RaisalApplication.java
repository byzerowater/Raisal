package me.fourground.raisal;

import android.app.Application;
import android.content.Context;

import com.zerowater.environment.BuildConfig;

import me.fourground.raisal.injection.component.ApplicationComponent;
import me.fourground.raisal.injection.component.DaggerApplicationComponent;
import me.fourground.raisal.injection.module.ApplicationModule;

import timber.log.Timber;

public class RaisalApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static RaisalApplication get(Context context) {
        return (RaisalApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}

