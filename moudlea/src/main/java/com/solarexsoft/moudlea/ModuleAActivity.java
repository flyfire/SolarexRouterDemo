package com.solarexsoft.moudlea;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.solarexsoft.base.ComponentShareProvider;
import com.solarexsoft.solarexrouter.annotation.SolarexRouter;
import com.solarexsoft.solarexroutercore.SolarexRouterCore;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 09:09/2020/3/5
 *    Desc:
 * </pre>
 */
@SolarexRouter(path = "/modulea/demo")
public class ModuleAActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulea_demo);
        findViewById(R.id.btn_jump_main).setOnClickListener(this);
        findViewById(R.id.btn_jump_moduleb).setOnClickListener(this);
        findViewById(R.id.btn_call_main).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_jump_main) {
            if (BuildConfig.isModuleA) {
                SolarexRouterCore.getInstance().build("/main/mainpage").navigation();
            } else {
                Toast.makeText(this, "独立模式", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btn_jump_moduleb) {
            if (BuildConfig.isModuleA) {
                SolarexRouterCore.getInstance().build("/moduleb/demo").navigation();
            } else {
                Toast.makeText(this, "独立模式", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btn_call_main) {
            if (BuildConfig.isModuleA) {
                ((ComponentShareProvider)(SolarexRouterCore.getInstance().build("/main/mainprovider").navigation())).callRemote();
            } else {
                Toast.makeText(this, "独立模式", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
