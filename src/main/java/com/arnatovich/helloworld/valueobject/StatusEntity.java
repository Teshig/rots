package com.arnatovich.helloworld.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class StatusEntity {

    @JsonProperty("isRoomOccupied")
    private String isRoomOccupied;

    @JsonProperty("eventTime")
    private String eventTime;
}
