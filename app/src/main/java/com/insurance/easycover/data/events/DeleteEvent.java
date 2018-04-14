package com.insurance.easycover.data.events;

/**
 * Created by naveedali on 9/16/17.
 */

public class DeleteEvent implements BaseEvent {

    private SimpleEvent simpleEvent;

    public DeleteEvent(boolean pStatus, String pMessage) {
        simpleEvent = new SimpleEvent(pStatus, pMessage);

    }

    public DeleteEvent(boolean pStatus, String pMessage, int pId) {
        simpleEvent = new SimpleEvent(pStatus, pId, pMessage);

    }

    @Override
    public boolean getStatus() {
        return simpleEvent.status;
    }

    @Override
    public int getEventId() {
        return simpleEvent.id;
    }

    @Override
    public String getMessage() {
        return simpleEvent.message;
    }

}
