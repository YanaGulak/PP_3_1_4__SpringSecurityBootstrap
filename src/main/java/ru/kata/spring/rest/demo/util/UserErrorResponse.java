package ru.kata.spring.rest.demo.util;

public class UserErrorResponse {

    private String info;
    private long timestamp;

    public UserErrorResponse(String info, long timestamp) {
        this.info = info;
        this.timestamp = timestamp;
    }


    public UserErrorResponse() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
