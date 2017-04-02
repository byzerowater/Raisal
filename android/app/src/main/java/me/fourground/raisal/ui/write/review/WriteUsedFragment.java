package me.fourground.raisal.ui.write.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class WriteUsedFragment extends Fragment {


    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_store)
    TextView mTvStore;
    Unbinder unbinder;

    public static Fragment newInstance() {
        Fragment fragment = new WriteUsedFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wirte_review_used, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_btn_used, R.id.rl_btn_first})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_btn_used:
                break;
            case R.id.rl_btn_first:
                break;
        }
    }
}
