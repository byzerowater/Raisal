package me.fourground.litmus.ui.write.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fourground.litmus.R;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.data.model.RegisterAppRequest;
import me.fourground.litmus.data.model.StoreInfoData;
import me.fourground.litmus.ui.write.Checker;
import me.fourground.litmus.util.StringUtil;

/**
 * Created by YoungSoo Kim on 2017-03-26.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteNameFragment extends Fragment implements Checker {

//    private static final String ANDROID_STORE_URL_PREFIX = "play.google.com/store/apps/details?";


    Unbinder unbinder;
    @BindView(R.id.et_name)
    TextInputEditText mEtName;
    @BindView(R.id.til_name)
    TextInputLayout mTilName;
    @BindView(R.id.et_play_store_url)
    TextInputEditText mEtPlayStoreUrl;
    @BindView(R.id.til_play_store_url)
    TextInputLayout mTilPlayStoreUrl;

    public static Fragment newInstance() {
        Fragment fragment = new WriteNameFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wirte_app_name, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public boolean checkInputText() {
        boolean isPass = true;
        String name = mEtName.getText().toString();
        String storeUrl = mEtPlayStoreUrl.getText().toString();

        if (StringUtil.isEmpty(name)) {
            isPass = false;
            mTilName.setErrorEnabled(true);
            mTilName.setError(getString(R.string.text_error_input_name));
        } else {
            mTilName.setError("");
            mTilName.setErrorEnabled(false);
        }

        if (StringUtil.isEmpty(storeUrl)/* || !storeUrl.contains(ANDROID_STORE_URL_PREFIX)*/) {
            isPass = false;
            mTilPlayStoreUrl.setErrorEnabled(true);
            mTilPlayStoreUrl.setError(getString(R.string.text_error_store_url));
        } else {
            mTilPlayStoreUrl.setError("");
            mTilPlayStoreUrl.setErrorEnabled(false);
        }

        if (isPass) {
            setAppInfo(name, storeUrl);
        }

        return isPass;
    }

    private void setAppInfo(String name, String storeUrl) {
        RegisterAppRequest registerAppRequest = ((WriteAppAppraisalActivity) getActivity()).getRegisterAppRequest();
        registerAppRequest.setAppName(name);
        List<StoreInfoData> storeInfoDataList = new ArrayList<>();
        storeInfoDataList.add(new StoreInfoData(Const.STORE_TYPE_ADR, storeUrl));
        registerAppRequest.setDownInfo(storeInfoDataList);
    }

}
