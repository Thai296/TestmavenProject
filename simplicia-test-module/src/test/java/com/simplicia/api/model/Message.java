package com.simplicia.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private long id;
    private String subject;

    public long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
