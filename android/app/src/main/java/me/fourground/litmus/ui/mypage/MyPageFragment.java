package me.fourground.litmus.ui.mypage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.fourground.litmus.R;
import me.fourground.litmus.data.BusEvent;
import me.fourground.litmus.ui.base.BaseFragment;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.ui.main.MainActivity;
import me.fourground.litmus.ui.mypage.app.MyAppActivity;
import me.fourground.litmus.ui.mypage.nickname.MyNicknameActivity;
import me.fourground.litmus.ui.mypage.review.MyReviewActivity;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyPageFragment extends BaseFragment implements MyPageMvpView {

    @Inject
    Bus mEventBus;
    @Inject
    MyPagePresenter mMyPagePresenter;
    @Inject
    LoadingDialog mLoadingDialog;

    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    Unbinder unbinder;

    public static Fragment newInstance() {
        Fragment fragment = new MyPageFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        fragmentComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);
        mMyPagePresenter.attachView(this);
        mEventBus.register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTvNickname.setText(mMyPagePresenter.getNickName());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mMyPagePresenter.detachView();
        mEventBus.unregister(this);
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {
    }

    @Subscribe
    public void onUpdateNicknameCompleted(BusEvent.UpdateNicknameCompleted event) {
        mTvNickname.setText(event.getNickname());
    }


//    private void signout() {
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (currentUser != null) {
//            String loginType = Const.LOGIN_TYPE_GOOGLE;
//            for (UserInfo userInfo : currentUser.getProviderData()) {
//                if ("facebook.com".equals(userInfo.getProviderId())) {
//                    loginType = Const.LOGIN_TYPE_FACEBOOK;
//                    break;
//                }
//            }
//
//            FirebaseAuth.getInstance().signOut();
//            if (loginType == Const.LOGIN_TYPE_FACEBOOK) {
//                LoginManager.getInstance().logOut();
//                ((MainActivity) getActivity()).selectHomeMenu();
//            } else {
//                showProgress(true);
//                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build();
//
//                Auth.GoogleSignInApi.signOut(new GoogleApiClient.Builder(getActivity())
//                        .enableAutoManage(getActivity() /* FragmentActivity */, connectionResult -> {
//                            Timber.d("onConnectionFailed:" + connectionResult);
//                        } /* OnConnectionFailedListener */)
//                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                        .build()).setResultCallback(status -> {
//                    Timber.i("status " + status.isSuccess());
//                    if (status.isSuccess()) {
//                        ((MainActivity) getActivity()).selectHomeMenu();
//                    }
//                    showProgress(false);
//                });
//            }
//        }
//    }

    @OnClick({R.id.btn_change_nickname, R.id.rl_btn_my_app, R.id.rl_btn_my_review, R.id.rl_btn_signout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_nickname:
                startActivity(MyNicknameActivity.getStartIntent(getActivity()));
                break;
            case R.id.rl_btn_my_app:
                startActivity(MyAppActivity.getStartIntent(getActivity()));
                break;
            case R.id.rl_btn_my_review:
                startActivity(MyReviewActivity.getStartIntent(getActivity()));
                break;
            case R.id.rl_btn_signout:
                mMyPagePresenter.signout();
                break;
        }
    }

    @Override
    public void onSignout() {
        ((MainActivity) getActivity()).selectHomeMenu();
    }
}
