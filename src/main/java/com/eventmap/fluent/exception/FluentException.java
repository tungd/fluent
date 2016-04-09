package com.eventmap.fluent.exception;

/**
 * Created by huytran on 4/10/16.
 */
public class FluentException extends Exception{

    private static final long serialVersionUID = 1L;

    public FluentException(){
        super("Network trouble, services are not available");
    }
}
