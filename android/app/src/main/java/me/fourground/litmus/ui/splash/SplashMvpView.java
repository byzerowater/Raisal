package me.fourground.litmus.ui.splash;


import me.fourground.litmus.ui.base.MvpView;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public interface SplashMvpView extends MvpView {

    void onSignIn();
    void onGoMain();
    void onGoSign();

}
