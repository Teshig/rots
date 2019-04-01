package com.arnatovich.helloworld.services;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class EventObject {
    private Boolean roomStatus;
    private Date lastActivity;
}
