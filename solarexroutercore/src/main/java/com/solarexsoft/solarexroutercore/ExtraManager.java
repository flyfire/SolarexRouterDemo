package com.solarexsoft.solarexroutercore;

import android.app.Activity;
import android.util.LruCache;

import com.solarexsoft.solarexroutercore.template.IExtra;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 19:25/2020/3/3
 *    Desc:
 * </pre>
 */

public class ExtraManager {
    public static final String EXTRA_CLASS_SUFFIX = "$$Extra";
    private static volatile ExtraManager instance;
    private LruCache<String, IExtra> extraInstancesCache;

    public static ExtraManager getInstance() {
        if (instance == null) {
            synchronized (ExtraManager.class) {
                if (instance == null) {
                    instance = new ExtraManager();
                }
            }
        }
        return instance;
    }

    private ExtraManager() {
        extraInstancesCache = new LruCache<>(64);
    }

    public void loadExtras(Activity activity) {
        String activityClassName = activity.getClass().getName();
        IExtra extraInstance = extraInstancesCache.get(activityClassName);
        try {
            if (extraInstance == null) {
                extraInstance = (IExtra) Class.forName(activityClassName + EXTRA_CLASS_SUFFIX).newInstance();
                extraInstancesCache.put(activityClassName, extraInstance);
            }
            extraInstance.injectExtra(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
