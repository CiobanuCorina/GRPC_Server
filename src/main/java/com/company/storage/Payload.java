package com.company.storage;

public class Payload {
    private String topic;
    private String type;
    private String message;

    public Payload(String topic, String type, String message) {
        this.topic = topic;
        this.type = type;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
