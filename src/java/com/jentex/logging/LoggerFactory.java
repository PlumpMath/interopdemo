package com.jentex.logging;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jensmith
 * Date: 03/02/2013
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class LoggerFactory {
    private final String name;
    private Writer out;

    public LoggerFactory(String name) {
        this.name = name;
    }

    public Logger build(){
        Logger logger = new Logger(name);
        logger.setLogDestination(new WriterLogDestination(out));
        return logger;
    }

    public LoggerFactory withOutBoundTo(java.io.Writer writer){
        out = writer;
        return this;
    }

    public LoggerFactory withConfig(HashMap<String,Object> configs){
        return this;
    }
}

