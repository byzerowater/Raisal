package me.fourground.litmus.ui.base;

import android.support.v4.app.Fragment;

import me.fourground.litmus.LitmuslApplication;
import me.fourground.litmus.injection.component.DaggerFragmentComponent;
import me.fourground.litmus.injection.component.FragmentComponent;
import me.fourground.litmus.injection.module.FragmentModule;


/**
 * Created by YoungSoo Kim on 2016-08-17.
 * 4ground Ltd
 * byzerowater@gmail.com
 * BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    /**
     * FragmentComponent
     */
    private FragmentComponent mFragmentComponent;

    /**
     * FragmentComponent 생성
     *
     * @return FragmentComponent
     */
    protected FragmentComponent fragmentComponent() {
        if (mFragmentComponent == null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .applicationComponent(LitmuslApplication.get(getActivity()).getComponent())
                    .fragmentModule(new FragmentModule(this))
                    .build();
        }

        return mFragmentComponent;
    }

}
