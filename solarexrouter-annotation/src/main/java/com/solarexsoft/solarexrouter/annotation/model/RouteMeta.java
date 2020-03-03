package com.solarexsoft.solarexrouter.annotation.model;

import javax.lang.model.element.Element;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 18:01/2020/3/3
 *    Desc:
 * </pre>
 */

public class RouteMeta {
    public enum JumpType {
        ACTIVITY,
        PROVIDER;
    }

    private JumpType jumpType;
    private Element element;
    private Class<?> destination;
    private String path;
    private String group;

    public RouteMeta(){}

    public RouteMeta(JumpType jumpType, Element element, Class<?> destination, String path, String group) {
        this.jumpType = jumpType;
        this.element = element;
        this.destination = destination;
        this.path = path;
        this.group = group;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public JumpType getJumpType() {
        return jumpType;
    }

    public Element getElement() {
        return element;
    }

    public Class<?> getDestination() {
        return destination;
    }

    public String getPath() {
        return path;
    }

    public String getGroup() {
        return group;
    }
}
