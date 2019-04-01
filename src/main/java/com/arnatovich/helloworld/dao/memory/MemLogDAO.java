package com.arnatovich.helloworld.dao.memory;

import com.arnatovich.helloworld.dao.ILogDAO;
import com.arnatovich.helloworld.services.LogObject;

import java.util.ArrayList;
import java.util.List;

public class MemLogDAO implements ILogDAO {

  private List<LogObject> logs = new ArrayList<>();

  @Override
  public LogObject getLastLog() {
    return logs.get(logs.size() - 1);
  }

  @Override
  public List<LogObject> getAllLogs() {
    return logs;
  }
}
