package com.adobe.demo.pubsub;

import java.util.Date;

public class CustomEvent {
    String message;
    Date eventDate;

    public CustomEvent(String message, Date eventDate) {
        this.message = message;
        this.eventDate = eventDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "CustomEvent{" +
                "message='" + message + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
