package com.solarexsoft.solarexrouterdemo;

import android.util.Log;

import com.solarexsoft.base.ComponentShareProvider;
import com.solarexsoft.solarexrouter.annotation.SolarexRouter;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 10:02/2020/3/5
 *    Desc:
 * </pre>
 */
@SolarexRouter(path = "/main/mainprovider")
public class DemoServiceProvider implements ComponentShareProvider {
    private static final String TAG = "DemoServiceProvider";
    @Override
    public void callRemote() {
        Log.d(TAG, "callRemote");
    }
}
