package com.solarexsoft.moudleb;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.solarexsoft.solarexrouter.annotation.SolarexRouter;
import com.solarexsoft.solarexroutercore.SolarexRouterCore;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 09:08/2020/3/5
 *    Desc:
 * </pre>
 */
@SolarexRouter(path = "/moduleb/demo")
public class ModuleBActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moduleb_demo);
        findViewById(R.id.btn_jump_main).setOnClickListener(this);
        findViewById(R.id.btn_jump_modulea).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_jump_main) {
            if (BuildConfig.isModuleB) {
                SolarexRouterCore.getInstance().build("/main/mainpage").navigation();
            } else {
                Toast.makeText(this, "独立模式", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btn_jump_modulea) {
            if (BuildConfig.isModuleB) {
                SolarexRouterCore.getInstance().build("/modulea/demo").navigation();
            } else {
                Toast.makeText(this, "独立模式", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
