package com.sistem.blog.controller;

import java.util.Date;

public class DetailsError {
    private Date created_at;
    private String message;
    private String details;

    public DetailsError(Date created_at, String message, String details) {
        this.created_at = created_at;
        this.message = message;
        this.details = details;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
