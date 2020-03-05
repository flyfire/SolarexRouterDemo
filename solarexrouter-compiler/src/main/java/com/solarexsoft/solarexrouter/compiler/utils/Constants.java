package com.solarexsoft.solarexrouter.compiler.utils;

import com.squareup.javapoet.ClassName;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 18:11/2020/3/3
 *    Desc:
 * </pre>
 */

public class Constants {

    public static final ClassName ROUTER = ClassName.get("com.solarexsoft.solarexroutercore", "SolarexRouterCore");
    public static final String ARGUMENTS_NAME = "moduleName";
    public static final String ANN_TYPE_ROUTE = "com.solarexsoft.solarexrouter.annotation.SolarexRouter";
    public static final String ANN_TYPE_EXTRA = "com.solarexsoft.solarexrouter.annotation.SolarexExtra";

    public static final String IROUTE_GROUP = "com.solarexsoft.solarexroutercore.template.IRouteGroup";
    public static final String IROUTE_ROOT = "com.solarexsoft.solarexroutercore.template.IRouteRoot";
    public static final String IEXTRA = "com.solarexsoft.solarexroutercore.template.IExtra";
    public static final String IPROVIDER = "com.solarexsoft.solarexroutercore.template.IProvider";

    public static final String ACTIVITY = "android.app.Activity";
    public static final String PARCELABLE = "android.os.Parcelable";

    public static final String METHOD_LOAD_INTO = "loadInto";
    public static final String METHOD_LOAD_EXTRA = "injectExtra";

    private static final String LANG = "java.lang.";
    public static final String BYTE = LANG + "Byte";
    public static final String SHORT = LANG + "Short";
    public static final String INTEGER = LANG + "Integer";
    public static final String LONG = LANG + "Long";
    public static final String FLOAT = LANG + "Float";
    public static final String DOUBLE = LANG + "Double";
    public static final String BOOLEAN = LANG + "Boolean";
    public static final String STRING = LANG + "String";

    public static final String BYTE_ARRAY = "byte[]";
    public static final String SHORT_ARRAY = "short[]";
    public static final String BOOLEAN_ARRAY = "boolean[]";
    public static final String CHAR_ARRAY = "char[]";
    public static final String DOUBLE_ARRAY = "double[]";
    public static final String FLOAT_ARRAY = "float[]";
    public static final String INT_ARRAY = "int[]";
    public static final String LONG_ARRAY = "long[]";
    public static final String STRING_ARRAY = "java.lang.String[]";

    public static final String ARRAYLIST = "java.util.ArrayList";
    public static final String LIST = "java.util.List";

    public static final String SEPARATOR = "$$";
    public static final String ROUTE_PREFIX = "SolarexRouter";

    public static final String ROUTE_ROOT_NAME = ROUTE_PREFIX + SEPARATOR + "Root" + SEPARATOR;
    public static final String ROUTE_GROUP_NAME = ROUTE_PREFIX + SEPARATOR + "Group" + SEPARATOR;
    public static final String ROUTE_EXTRA_NAME = SEPARATOR + "Extra";

    public static final String ROUTE_PACKAGE_NAME = "com.solarexsoft.router.routes";
}
