package com.arnatovich.helloworld.dao;

import com.arnatovich.helloworld.services.LogObject;

import java.util.List;

public interface ILogDAO {

  LogObject getLastLog();

  List<LogObject> getAllLogs();

}
