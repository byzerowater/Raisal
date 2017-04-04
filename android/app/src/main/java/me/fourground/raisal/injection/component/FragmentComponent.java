package me.fourground.raisal.injection.component;


import dagger.Component;
import me.fourground.raisal.injection.PerFragment;
import me.fourground.raisal.injection.module.FragmentModule;
import me.fourground.raisal.ui.review.ReviewListFragment;
import me.fourground.raisal.ui.write.review.WritePointFragment;

/**
 * Created by YoungSoo Kim on 2016-06-23.
 * 4ground Ltd
 * byzerowater@gmail.com
 * Dagger FragmentComponent
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(WritePointFragment writePointFragment);

    void inject(ReviewListFragment reviewListFragment);
}

