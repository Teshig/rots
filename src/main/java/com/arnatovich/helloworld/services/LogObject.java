package com.arnatovich.helloworld.services;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogObject {
    private LocalDateTime busyUpTo;
    private LocalDateTime busySince;

    public LogObject(LocalDateTime lastTime, LocalDateTime firstTime) {
        this.busyUpTo = lastTime;
        this.busySince = firstTime;
    }
}
