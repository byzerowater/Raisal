package me.fourground.raisal.ui.write.app;

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
import me.fourground.raisal.R;
import me.fourground.raisal.common.Const;
import me.fourground.raisal.data.model.RegisterAppRequest;
import me.fourground.raisal.data.model.StoreInfoData;
import me.fourground.raisal.ui.write.Checker;
import me.fourground.raisal.util.StringUtil;

/**
 * Created by YoungSoo Kim on 2017-03-26.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteNameFragment extends Fragment implements Checker {


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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public boolean checkInputText() {
        String name = mEtName.getText().toString();
        String storeUrl = mEtPlayStoreUrl.getText().toString();

        RegisterAppRequest registerAppRequest = ((WriteAppAppraisalActivity) getActivity()).getRegisterAppRequest();

        registerAppRequest.setAppName(name);

        List<StoreInfoData> storeInfoDataList = new ArrayList<>();

        storeInfoDataList.add(new StoreInfoData(Const.STORE_TYPE_ADR, mEtPlayStoreUrl.getText().toString()));

        registerAppRequest.setDownInfo(storeInfoDataList);

        return !StringUtil.isEmpty(name) && !StringUtil.isEmpty(storeUrl);
    }
}
