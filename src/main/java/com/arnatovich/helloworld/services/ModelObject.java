package com.arnatovich.helloworld.services;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ModelObject {
    private Boolean roomStatus;
    private String lastActivity;
}
