package com.jentex.logging;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jensmith
 * Date: 03/02/2013
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class Logger {
    private final String name;

    public Logger(String name) {

        this.name = name;
    }

    public void logMessage(LogMessage message){
        System.out.println(name + "-" + message.createLogMessage());
    }

    public void logAribtrary(HashMap<String, Object> message){
        StringBuilder messageBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : message.entrySet()) {
            messageBuilder.append(entry.getKey());
            messageBuilder.append("=");
            messageBuilder.append(entry.getValue());
            messageBuilder.append(" ");
        }
        System.out.println(name + "-" + messageBuilder.toString());

    }
}
