package com.insurance.easycover.data.events;

import java.util.List;

/**
 * Created by naveedali on 9/9/17.
 */

public class ListDataEvent<T> implements BaseEvent {

    private SimpleEvent simpleEvent;
    public List<T> listData;

    public ListDataEvent(boolean pStatus, int pId, String pMessage) {
        simpleEvent = new SimpleEvent(pStatus, pId, pMessage);
    }

    public ListDataEvent(boolean pStatus, int pId, String pMessage, List<T> listData) {
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
}
