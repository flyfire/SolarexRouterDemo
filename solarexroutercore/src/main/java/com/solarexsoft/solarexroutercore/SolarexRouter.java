package com.solarexsoft.solarexroutercore;

import android.app.Application;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.solarexsoft.solarexroutercore.template.IRouteGroup;
import com.solarexsoft.solarexroutercore.template.IRouteRoot;
import com.solarexsoft.solarexroutercore.utils.FindClassesUtils;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 20:07/2020/3/3
 *    Desc:
 * </pre>
 */

public class SolarexRouter {
    private static final String TAG = "SolarexRouter";
    private static final String ROUTE_ROOT_PACKAGE_NAME = "com.solarexsoft.router.routes";
    private static final String ROUTE_PREFIX_NAME = "SolarexRouter";
    private static final String SEPARATOR = "$$";
    private static final String ROUTE_ROOT_SUFFIX = "Root";
    private boolean debug = false;

    private static Application mContext;
    private static volatile SolarexRouter instance;

    private SolarexRouter() {
    }

    public void openDebug() {
        debug = true;
    }

    public static SolarexRouter getInstance() {
        if (instance == null) {
            synchronized (SolarexRouter.class) {
                if (instance == null) {
                    instance = new SolarexRouter();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        mContext = application;
        try {
            loadRouteGroupInfo();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "SolarexRouter initialize error", e);
        }
    }

    private void loadRouteGroupInfo() throws PackageManager.NameNotFoundException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Set<String> routerClasses = FindClassesUtils.findClassNamesByPackageName(mContext, ROUTE_ROOT_PACKAGE_NAME);
        for (String routerClass : routerClasses) {
            if (routerClass.startsWith(ROUTE_ROOT_PACKAGE_NAME + "." + ROUTE_PREFIX_NAME + SEPARATOR + ROUTE_ROOT_SUFFIX)) {
                ((IRouteRoot)(Class.forName(routerClass).newInstance())).loadInto(RouteTableHolder.groupRouteIndex);
            }
        }
        if (debug) {
            for (Map.Entry<String, Class<? extends IRouteGroup>> entry : RouteTableHolder.groupRouteIndex.entrySet()) {
                Log.d(TAG, "Group: name -> " + entry.getKey() + ", clz -> " + entry.getValue().getName());
            }
        }
    }

    public PostCard build(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("illegal route path: " + path);
        } else {
            return build(path, extractGroupFromPath(path));
        }
    }

    private String extractGroupFromPath(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new RuntimeException("illegal route path: " + path);
        }
        String group = path.substring(1, path.indexOf("/", 1));
        return group;
    }

    public PostCard build(String path, String group) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(group)) {
            throw new RuntimeException("illegal route path: " + path + ",group: " + group);
        } else {
            return new PostCard(path, group);
        }
    }
}
