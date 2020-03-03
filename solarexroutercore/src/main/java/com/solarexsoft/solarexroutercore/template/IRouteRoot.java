package com.solarexsoft.solarexroutercore.template;

import java.util.Map;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 18:19/2020/3/3
 *    Desc:
 * </pre>
 */

public interface IRouteRoot {
    void loadInto(Map<String, Class<? extends  IRouteGroup>> routeGroups);
}
