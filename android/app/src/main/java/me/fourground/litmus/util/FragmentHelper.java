package me.fourground.litmus.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by YoungSoo Kim on 2017-04-02.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class FragmentHelper {

    public static void addFragment(int contentFrame, FragmentActivity activity, Fragment addFragment) {
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(contentFrame);

        if (fragment == null) {
            manager.beginTransaction()
                    .add(contentFrame, addFragment)
                    .commit();

        } else {
            manager.beginTransaction()
                    .replace(contentFrame, addFragment)
                    .commit();
        }
    }

    public static void addToBackStackFragment(int contentFrame, FragmentActivity activity, Fragment addFragment) {
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(contentFrame);
        if (fragment == null) {
            manager.beginTransaction()
                    .add(contentFrame, addFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            manager.beginTransaction()
                    .add(contentFrame, addFragment)
                    .hide(fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public static Fragment getFragment(int contentFrame, FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(contentFrame);

        return fragment;

    }
}
