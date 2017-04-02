package me.fourground.raisal.ui.write.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fourground.raisal.R;
import me.fourground.raisal.data.model.RegisterAppRequest;
import me.fourground.raisal.ui.write.Checker;

/**
 * Created by YoungSoo Kim on 2017-03-26.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteDescriptionFragment extends Fragment implements Checker {

    @BindView(R.id.et_description)
    TextInputEditText mEtDescription;
    @BindView(R.id.til_description)
    TextInputLayout mTilDescription;
    Unbinder unbinder;
    @BindView(R.id.rg_days)
    RadioGroup mRgDays;

    public static Fragment newInstance() {
        Fragment fragment = new WriteDescriptionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wirte_app_description, container, false);
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
        int checkedRadioButtonId = mRgDays.getCheckedRadioButtonId();

        String term = checkedRadioButtonId == R.id.btn_7day ? "7" : "14";

        RegisterAppRequest registerAppRequest = ((WriteWriteAppAppraisalActivity) getActivity()).getRegisterAppRequest();
        registerAppRequest.setAppDesc(mEtDescription.getText().toString());
        registerAppRequest.setReqTerm(term);

        return true;
    }
}