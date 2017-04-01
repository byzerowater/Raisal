package me.fourground.raisal.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import me.fourground.raisal.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;
import me.fourground.raisal.ui.dialog.LoadingDialog;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

    @Provides
    FragmentManager providesFragmentManager() {
        return ((FragmentActivity) mActivity).getSupportFragmentManager();
    }

    @Provides
    LoadingDialog provideLoadingDialog() {
        return new LoadingDialog(mActivity);
    }

}