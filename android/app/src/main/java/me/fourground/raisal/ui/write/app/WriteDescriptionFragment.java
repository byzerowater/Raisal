package me.fourground.raisal.ui.write.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.fourground.raisal.R;

/**
 * Created by YoungSoo Kim on 2017-03-26.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteDescriptionFragment extends Fragment {

    @BindView(R.id.et_description)
    TextInputEditText mEtDescription;
    @BindView(R.id.til_description)
    TextInputLayout mTilDescription;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wirte_app_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_7day, R.id.btn_14day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_7day:
                break;
            case R.id.btn_14day:
                break;
        }
    }
}