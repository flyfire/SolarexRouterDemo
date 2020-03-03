package com.solarexsoft.solarexroutercore.exception;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 19:02/2020/3/3
 *    Desc:
 * </pre>
 */

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String message) {
        super(message);
    }
}
