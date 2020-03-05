package com.solarexsoft.moudlea;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.solarexsoft.solarexrouter.annotation.SolarexRouter;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 11:20/2020/3/5
 *    Desc:
 * </pre>
 */
@SolarexRouter(path = "/modulea/inner")
public class ModuleAInnerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulea_inner);
    }
}
