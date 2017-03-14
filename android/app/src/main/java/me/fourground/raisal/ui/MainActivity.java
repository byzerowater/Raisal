package me.fourground.raisal.ui;

import android.os.Bundle;

import com.zerowater.environment.R;
import me.fourground.raisal.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
