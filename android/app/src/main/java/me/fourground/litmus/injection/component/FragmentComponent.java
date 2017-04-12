package me.fourground.litmus.injection.component;


import dagger.Component;
import me.fourground.litmus.injection.PerFragment;
import me.fourground.litmus.injection.module.FragmentModule;
import me.fourground.litmus.ui.mypage.MyPageFragment;
import me.fourground.litmus.ui.app.AppListFragment;
import me.fourground.litmus.ui.write.review.WritePointFragment;

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

    void inject(AppListFragment reviewListFragment);

    void inject(MyPageFragment myPageFragment);
}

