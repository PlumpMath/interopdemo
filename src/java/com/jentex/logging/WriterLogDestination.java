package com.jentex.logging;

import java.io.IOException;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: jensmith
 * Date: 04/02/2013
 * Time: 07:13
 * To change this template use File | Settings | File Templates.
 */
public class WriterLogDestination implements LogDestination {
    private final Writer writer;

    public WriterLogDestination(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void send(String context, String message) {
        try {
            writer.append(context + ":::: " + message);
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
