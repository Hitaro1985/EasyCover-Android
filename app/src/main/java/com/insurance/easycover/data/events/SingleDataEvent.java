package com.insurance.easycover.data.events;

/**
 * Created by naveedali on 9/9/17.
 */

public class SingleDataEvent<T> implements BaseEvent {

    private SimpleEvent simpleEvent;
    public T data;


    public SingleDataEvent(boolean pStatus, int pId, String pMessage, T pData) {
        simpleEvent = new SimpleEvent(pStatus, pId, pMessage);
        this.data = pData;
    }


    @Override
    public int getEventId() {
        return simpleEvent.id;
    }

    @Override
    public boolean getStatus() {
        return simpleEvent.status;
    }

    @Override
    public String getMessage() {
        return simpleEvent.message;
    }
}
