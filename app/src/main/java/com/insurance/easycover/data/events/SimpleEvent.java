package com.insurance.easycover.data.events;

/**
 * Created by naveedali on 9/9/17.
 */

public class SimpleEvent implements BaseEvent{

    public int id;
    public String message;
    public boolean status;

    public SimpleEvent(boolean pStatus, String pMessage) {
        this.status = pStatus;
        this.message = pMessage;

    }

    public SimpleEvent(boolean pStatus, int pId, String pMessage) {
        this.status = pStatus;
        this.id = pId;
        this.message = pMessage;

    }


    @Override
    public int getEventId() {
        return id;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
