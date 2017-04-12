package me.fourground.litmus.injection.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import me.fourground.litmus.injection.ActivityContext;
import me.fourground.litmus.injection.PerFragment;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.util.LoadingHelper;

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

    @Provides
    LoadingDialog provideLoadingDialog() {
        return new LoadingDialog(mFragment.getActivity());
    }

    @Provides
    LoadingHelper provideLoadingManager() {
        return new LoadingHelper();
    }
}
