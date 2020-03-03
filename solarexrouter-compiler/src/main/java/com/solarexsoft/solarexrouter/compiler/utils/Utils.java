package com.solarexsoft.solarexrouter.compiler.utils;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 22:38/2020/3/3
 *    Desc:
 * </pre>
 */

public class Utils {
    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
