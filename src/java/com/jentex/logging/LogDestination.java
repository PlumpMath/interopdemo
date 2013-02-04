package com.jentex.logging;

/**
 * Created with IntelliJ IDEA.
 * User: jensmith
 * Date: 04/02/2013
 * Time: 07:09
 * To change this template use File | Settings | File Templates.
 */
public interface LogDestination {
    void send(String context, String message);
}
