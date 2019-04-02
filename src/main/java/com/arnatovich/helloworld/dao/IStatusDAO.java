package com.arnatovich.helloworld.dao;

import com.arnatovich.helloworld.valueobject.StatusEntity;

public interface IStatusDAO {

  void addStatus(StatusEntity status);

  StatusEntity getLastStatus();

  void clear();

  boolean isEmpty();

}
