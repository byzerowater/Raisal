package me.fourground.raisal.injection.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import me.fourground.raisal.injection.ActivityContext;
import me.fourground.raisal.injection.PerFragment;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.util.LoadingHelper;
import me.fourground.raisal.util.ToastManager;

/**
 * Created by YoungSoo Kim on 2016-09-02.
 * 4ground Ltd
 * byzerowater@gmail.com
 * FragmentModule
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mFragment.getActivity();
    }
}
