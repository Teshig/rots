package com.arnatovich.helloworld.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StatusEntity {

    @JsonProperty("isRoomOccupied")
    private Boolean isRoomOccupied;

    @JsonProperty("eventTime")
    private LocalDateTime eventTime;
}
