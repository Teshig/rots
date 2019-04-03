package com.arnatovich.helloworld.services;

import com.arnatovich.helloworld.dao.IStatusDAO;
import com.arnatovich.helloworld.dao.memory.MemStatusDAO;
import com.arnatovich.helloworld.valueobject.StatusEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class EventStoreService {

    private final static long TIME_THRESHOLD = 2 * 60 * 1000;  // 2 minutes

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
            if (!isOccupied) {
                fromTime = statusDAO.getLastStatus().getEventTime();
            }
        }
        return new ViewModel(isOccupied, fromTime, null);
    }
}
