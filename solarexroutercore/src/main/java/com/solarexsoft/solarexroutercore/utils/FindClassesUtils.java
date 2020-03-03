package com.solarexsoft.solarexroutercore.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.solarexsoft.solarexroutercore.thread.FindClassesThreadPoolExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

import dalvik.system.DexFile;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 18:24/2020/3/3
 *    Desc:
 * </pre>
 */

public class FindClassesUtils {
    public static List<String> getSourcePaths(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (applicationInfo.splitSourceDirs != null) {
                sourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
            }
        }
        return sourcePaths;
    }

    public static ConcurrentMap<String, Object> findClassNamesByPackageName(Application context, final String packageName) throws PackageManager.NameNotFoundException, InterruptedException {
        ConcurrentMap<String, Object> classNames = new ConcurrentHashMap<>();
        Object DUMB_OBJ = new Object();
        List<String> sourcePaths = getSourcePaths(context);
        final CountDownLatch countDownLatch = new CountDownLatch(sourcePaths.size());
        ThreadPoolExecutor threadPoolExecutor = FindClassesThreadPoolExecutor.newThreadPoolExecutor(sourcePaths.size());
        for (final String sourcePath : sourcePaths) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    DexFile dexFile = null;
                    try {
                        dexFile = new DexFile(sourcePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (dexFile != null) {
                            try {
                                dexFile.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            countDownLatch.countDown();
                        }
                    }
                }
            });
        }
        countDownLatch.await();
        return classNames;
    }
}
