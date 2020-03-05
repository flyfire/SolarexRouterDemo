package com.solarexsoft.modulea.independent;

import android.app.Application;

import com.solarexsoft.solarexroutercore.SolarexRouterCore;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 11:15/2020/3/5
 *    Desc:
 * </pre>
 */

public class MoudleAApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SolarexRouterCore.getInstance().init(this);
    }
}
