package com.arnatovich.helloworld.dao.memory;

import com.arnatovich.helloworld.dao.IStatusDAO;
import com.arnatovich.helloworld.valueobject.StatusEntity;

import java.util.ArrayList;
import java.util.List;

public class MemStatusDAO implements IStatusDAO {

  private List<StatusEntity> events = new ArrayList<>();

  @Override
  public synchronized void addStatus(StatusEntity event) {
      events.add(event);
  }

  @Override
  public synchronized StatusEntity getLastStatus() {
    return events.get(events.size() - 1);
  }

  @Override
  public synchronized void clear() {
    events = new ArrayList<>();
  }

  @Override
  public boolean isEmpty() {
    return events.isEmpty();
  }
}
