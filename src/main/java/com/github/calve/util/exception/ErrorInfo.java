package com.github.calve.util.exception;

import java.io.Serializable;

public class ErrorInfo implements Serializable {
    private final String typeMessage;
    private final String[] details;

    public ErrorInfo(String typeMessage, String[] details) {
        this.typeMessage = typeMessage;
        this.details = details;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public String[] getDetails() {
        return details;
    }
}
