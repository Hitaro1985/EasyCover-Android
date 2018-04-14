package com.insurance.easycover.data.events;

import java.util.List;

/**
 * Created by PDC100 on 3/14/2018.
 */

public class JobEvent<T> implements BaseEvent {

    private SimpleEvent simpleEvent;
    public List<T> listData;

    public JobEvent(boolean pStatus, int pId, String pMessage) {
        simpleEvent = new SimpleEvent(pStatus, pId, pMessage);
    }

    public JobEvent(boolean pStatus, int pId, String pMessage, List<T> listData) {
        this(pStatus, pId, pMessage);
        this.listData = listData;

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

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }
}