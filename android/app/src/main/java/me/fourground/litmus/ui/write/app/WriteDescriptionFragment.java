package me.fourground.litmus.ui.write.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.fourground.litmus.R;
import me.fourground.litmus.data.model.RegisterAppRequest;
import me.fourground.litmus.ui.write.Checker;

/**
 * Created by YoungSoo Kim on 2017-03-26.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteDescriptionFragment extends Fragment implements Checker {

    private static final int DAY_TYEP_7 = 0;
    private static final int DAY_TYEP_14 = 1;

    @BindView(R.id.et_description)
    TextInputEditText mEtDescription;
    @BindView(R.id.til_description)
    TextInputLayout mTilDescription;
    Unbinder unbinder;
    @BindViews({R.id.btn_7day, R.id.btn_14day})
    View[] mDayViews;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        selectButton(DAY_TYEP_14);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public boolean checkInputText() {

        String term = getSelectedButtonId() == R.id.btn_7day ? "7" : "14";

        RegisterAppRequest registerAppRequest = ((WriteAppAppraisalActivity) getActivity()).getRegisterAppRequest();
        registerAppRequest.setAppDesc(mEtDescription.getText().toString());
        registerAppRequest.setReqTerm(term);

        return true;
    }

    private void selectButton(int position) {

        int size = mDayViews.length;

        for (int i = 0; i < size; i++) {
            mDayViews[i].setSelected(position == i);
        }
    }

    private int getSelectedButtonId() {
        int id = 0;

        for (View dayView : mDayViews) {
            if (dayView.isSelected()) {
                id = dayView.getId();
                break;
            }
        }
        return id;
    }

    @OnClick({R.id.btn_7day, R.id.btn_14day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_7day:
                selectButton(DAY_TYEP_7);
                break;
            case R.id.btn_14day:
                selectButton(DAY_TYEP_14);
                break;
        }
    }
}