package com.solarexsoft.solarexroutercore;

import com.solarexsoft.solarexrouter.annotation.model.RouteMeta;
import com.solarexsoft.solarexroutercore.template.IProvider;
import com.solarexsoft.solarexroutercore.template.IRouteGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 19:17/2020/3/3
 *    Desc:
 * </pre>
 */

public class RouteTableHolder {
    // root 映射表，key为group名，value为group表class名
    static Map<String, Class<? extends IRouteGroup>> groupRouteIndex = new HashMap<>();
    // group 映射表，key为route path,value为跳转信息
    static Map<String, RouteMeta> singleGroupRoutes = new HashMap<>();
    // group 映射表，保存远程(跨module)调用服务类实例
    static Map<Class, IProvider> providers = new HashMap<>();
}
