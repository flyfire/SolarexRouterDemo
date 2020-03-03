package com.solarexsoft.solarexroutercore.template;

import com.solarexsoft.solarexrouter.annotation.model.RouteMeta;

import java.util.Map;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 18:18/2020/3/3
 *    Desc:
 * </pre>
 */

public interface IRouteGroup {
    void loadInto(Map<String, RouteMeta> groupRouteMetas);
}
