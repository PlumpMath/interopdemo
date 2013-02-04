package com.jentex.logging;

/**
 * Created with IntelliJ IDEA.
 * User: jensmith
 * Date: 03/02/2013
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class LoggerFactory {
    private final String name;

    public LoggerFactory(String name) {
        this.name = name;
    }

    public Logger build(){
        return new Logger(name);
    }
}

