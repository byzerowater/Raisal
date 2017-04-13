package me.fourground.litmus;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import me.fourground.litmus.data.BusEvent;
import me.fourground.litmus.data.DataManager;
import me.fourground.litmus.injection.component.ApplicationComponent;
import me.fourground.litmus.injection.component.DaggerApplicationComponent;
import me.fourground.litmus.injection.module.ApplicationModule;
import me.fourground.litmus.ui.signin.SignInActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class LitmuslApplication extends Application {

    @Inject
    Bus mEventBus;
    @Inject
    DataManager mDataManager;
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mApplicationComponent.inject(this);
        mEventBus.register(this);
    }

    public static LitmuslApplication get(Context context) {
        return (LitmuslApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Subscribe
    public void onAuthenticationError(BusEvent.AuthenticationError event) {
        mDataManager.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(s -> {
                    startActivity(SignInActivity.getStartIntent(this, true));
                });
    }
}

