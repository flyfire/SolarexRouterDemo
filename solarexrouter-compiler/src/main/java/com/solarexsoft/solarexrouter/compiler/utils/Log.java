package com.solarexsoft.solarexrouter.compiler.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 22:41/2020/3/3
 *    Desc:
 * </pre>
 */

public class Log {
    private Messager messager;
    private Log(Messager messager) {
        this.messager = messager;
    }
    public static Log newLog(Messager messager) {
        return new Log(messager);
    }

    public void i(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
