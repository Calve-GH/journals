package com.github.calve.to.email;

import java.io.Serializable;

public class SentInfo implements Serializable {
    private final String email;
    private final String description;

    public SentInfo(String email, String description) {
        this.email = email;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }
}
