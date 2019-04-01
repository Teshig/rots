package com.arnatovich.helloworld.services;

import com.arnatovich.helloworld.valueobject.StatusEntity;

import java.util.ArrayList;
import java.util.List;

public class EventStore {
    public static final long TIME_OUT_GAP = 2 * 60;
    private List<StatusEntity> objectStore = new ArrayList<>();
    private List<LogObject> eventStore = new ArrayList<>();

    public void addEvent(StatusEntity event) {
//        objectStore.add(event);

    }

    public StatusEntity getLastEvent() {
        if (objectStore.isEmpty()) {
            return null;
        }
        return objectStore.get(objectStore.size()-1);
    }

    public List<LogObject> getLogs(){
        return new ArrayList<>(eventStore);
    }

/*
    private void reniewStore(StatusEntity newEvent) {
        if (objectStore.isEmpty()) {
            return;
        }

        StatusEntity previousEntity = objectStore.get(objectStore.size() - 1);
        Duration duration = Duration.between(previousEntity.getEventTime(), newEvent.getEventTime());

        long pastSeconds = duration.getSeconds();
        if (TIME_OUT_GAP > pastSeconds) {
            updateLogs(previousEntity, objectStore.get(0));
            updateStore(newEvent);
        }
    }
*/

/*
    private void updateLogs(StatusEntity last, StatusEntity first) {
        LogObject log = new LogObject(last.getEventTime(), first.getEventTime());
        eventStore.add(log);
    }
*/

    private void updateStore(StatusEntity newEvent) {
        objectStore = new ArrayList<>();
        objectStore.add(newEvent);
    }
}
