package com.insurance.easycover.data.events;

/**
 * Created by naveedali on 11/12/17.
 */

public interface BaseEvent {
    int getEventId();
    boolean getStatus();
    String getMessage();
}
