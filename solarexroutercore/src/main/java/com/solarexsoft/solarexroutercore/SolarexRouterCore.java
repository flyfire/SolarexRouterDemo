package com.solarexsoft.solarexroutercore;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.solarexsoft.solarexrouter.annotation.model.RouteMeta;
import com.solarexsoft.solarexroutercore.callback.NavigationCallback;
import com.solarexsoft.solarexroutercore.exception.RouteNotFoundException;
import com.solarexsoft.solarexroutercore.template.IProvider;
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

public class SolarexRouterCore {
    private static final String TAG = "SolarexRouterCore";
    private static final String ROUTE_ROOT_PACKAGE_NAME = "com.solarexsoft.router.routes";
    private static final String ROUTE_PREFIX_NAME = "SolarexRouter";
    private static final String SEPARATOR = "$$";
    private static final String ROUTE_ROOT_SUFFIX = "Root";
    private boolean debug = false;

    private static Application mContext;
    private static volatile SolarexRouterCore instance;

    private SolarexRouterCore() {
    }

    public void openDebug() {
        debug = true;
    }

    public static SolarexRouterCore getInstance() {
        if (instance == null) {
            synchronized (SolarexRouterCore.class) {
                if (instance == null) {
                    instance = new SolarexRouterCore();
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

    protected Object navigation(Context context, PostCard postCard, int requestCode, NavigationCallback callback) {
        try {
            prepareCard(postCard);
        } catch (RouteNotFoundException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onLost(postCard);
            }
            return null;
        }
        if (callback != null) {
            callback.onFound(postCard);
        }
        switch (postCard.getJumpType()) {
            case ACTIVITY:
                Context currentContext = context == null ? mContext : context;
                Intent intent = new Intent(currentContext, postCard.getDestination());
                intent.putExtras(postCard.getExtras());
                int flags = postCard.getFlags();
                if (flags != -1) {
                    if (!(currentContext instanceof Activity)) {
                        flags |= Intent.FLAG_ACTIVITY_NEW_TASK;
                    }
                    intent.setFlags(flags);
                } else {
                    if (!(currentContext instanceof Activity)) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
                if (requestCode > 0) {
                    ActivityCompat.startActivityForResult((Activity)currentContext, intent, requestCode, postCard.getOptionsCompat());
                } else {
                    ActivityCompat.startActivity(currentContext, intent, postCard.getOptionsCompat());
                }
                if (postCard.getEnterAnim() != 0 && postCard.getExitAnim() != 0 && currentContext instanceof Activity) {
                    ((Activity) currentContext).overridePendingTransition(postCard.getEnterAnim(), postCard.getExitAnim());
                }
                if (callback != null) {
                    callback.onArrival(postCard);
                }
                return null;
            case PROVIDER:
                Class<?> destination = postCard.getDestination();
                IProvider providerInstance = RouteTableHolder.providers.get(destination);
                if (providerInstance == null) {
                    try {
                        providerInstance = (IProvider) destination.newInstance();
                        RouteTableHolder.providers.put(destination, providerInstance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                postCard.setProvider(providerInstance);
                /*
                if (callback != null) {
                    callback.onArrival(postCard);
                }*/
                return postCard.getProvider();
        }
        return null;
    }

    private void prepareCard(PostCard postCard) {
        RouteMeta routeMeta = RouteTableHolder.singleGroupRoutes.get(postCard.getPath());
        if (routeMeta == null) {
            Class<? extends IRouteGroup> groupMetaClz = RouteTableHolder.groupRouteIndex.get(postCard.getGroup());
            if (groupMetaClz == null) {
                throw new RouteNotFoundException("error find route info for path: " + postCard.getPath() + ", group: " + postCard.getGroup());
            }
            IRouteGroup groupInstance;
            try {
                groupInstance = groupMetaClz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("error instance group route table for path: " + postCard.getPath(), e);
            }
            groupInstance.loadInto(RouteTableHolder.singleGroupRoutes);
            RouteTableHolder.groupRouteIndex.remove(postCard.getGroup());
            prepareCard(postCard);
        } else {
            postCard.setDestination(routeMeta.getDestination());
            postCard.setJumpType(routeMeta.getJumpType());
        }
    }

    public void injectExtras(Activity activity) {
        ExtraManager.getInstance().loadExtras(activity);
    }
}
