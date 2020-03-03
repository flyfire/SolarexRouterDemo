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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public static Set<String> findClassNamesByPackageName(Application context, final String packageName) throws PackageManager.NameNotFoundException, InterruptedException {
        final Set<String> classNames = Collections.synchronizedSet(new HashSet<String>());
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
                        Enumeration<String> entries = dexFile.entries();
                        while (entries.hasMoreElements()) {
                            String element = entries.nextElement();
                            if (element.startsWith(packageName)) {
                                classNames.add(element);
                            }
                        }
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
