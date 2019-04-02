package com.arnatovich.helloworld.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ViewModel {

  private boolean isOccupied;
  private Date fromTime;
  private Date tillTime;

}
