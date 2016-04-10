package com.eventmap.fluent.exception;

/**
 * Created by huytran on 4/10/16.
 */
public class FluentException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FluentException(Exception cause) {
        super(cause);
    }
}
