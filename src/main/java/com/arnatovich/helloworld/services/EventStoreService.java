package com.arnatovich.helloworld.services;

import com.arnatovich.helloworld.dao.IStatusDAO;
import com.arnatovich.helloworld.dao.memory.MemStatusDAO;
import com.arnatovich.helloworld.valueobject.StatusEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.arnatovich.helloworld.services.EventStore.TIME_OUT_GAP;

@Service
@Slf4j
public class EventStoreService {

    private final static long TIME_THRESHOLD = 2 * 60 * 1000;  // 2 minutes

    private EventStore store = new EventStore();

    private IStatusDAO statusDAO = new MemStatusDAO();
    private Date fromTime;

    public synchronized void addStatus(StatusEntity status){
        Date eventTime = status.getEventTime();
        if (statusDAO.isEmpty() || eventTime.getTime() > statusDAO.getLastStatus().getEventTime().getTime() + TIME_THRESHOLD) {
            fromTime = eventTime;
            statusDAO.clear();
        }
        statusDAO.addStatus(status);
        log.info("StatusEntity " + status + " is added!");
    }

    public synchronized ViewModel getViewModel() {
        boolean isOccupied;
        if (statusDAO.isEmpty()) {
            isOccupied = false;
        } else {
            Date now = new Date();
            Date eventTime = statusDAO.getLastStatus().getEventTime();
            isOccupied = now.getTime() < eventTime.getTime() + TIME_THRESHOLD;
        }
        return new ViewModel(isOccupied, fromTime, null);
    }

    @Deprecated
    public EventObject getRoomStatus() {
        StatusEntity lastEvent = store.getLastEvent();

        EventObject eventObject = new EventObject();
        Boolean roomStatus = extractCurrentStatus(lastEvent);
        eventObject.setRoomStatus(roomStatus);

        // List<LogObject> logs = store.getLogs();
        //String lastActivity = extractLastActivity(logs);
        //modelObject.setLastActivity(lastActivity);

        return eventObject;
    }

    private String extractLastActivity(List<LogObject> logs) {
        if (logs.isEmpty()){
            return "Today room wasn't busy! Miracle!";
        }

        LogObject log = logs.get(logs.size() - 1);

        return log.getBusySince().toString();
    }

    private String extractLastActivity2(List<LogObject> logs) {
        if (logs.isEmpty()){
            return "Today room wasn't busy! Miracle!";
        }

        LogObject log = logs.get(logs.size() - 1);

        return log.getBusyUpTo().toString();
    }

    private boolean extractCurrentStatus(StatusEntity lastEvent) {
        if (lastEvent == null) {
          return false;
        }
        LocalDateTime eventTime = LocalDateTime.ofInstant(lastEvent.getEventTime().toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(eventTime, LocalDateTime.now());

        long pastSeconds = duration.getSeconds();
        if (TIME_OUT_GAP - pastSeconds > 0) {
            return true;
        }

        return false;
    }
}
