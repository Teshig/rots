package com.arnatovich.helloworld.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ViewModel {

  private boolean isOccupied;
  private Date fromTime;
  private Date tillTime;

}
