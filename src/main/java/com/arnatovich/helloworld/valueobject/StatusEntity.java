package com.arnatovich.helloworld.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StatusEntity {

    @JsonProperty("isRoomOccupied")
    private Boolean isRoomOccupied;

    @JsonProperty("eventTime")
    private Date eventTime;
}
