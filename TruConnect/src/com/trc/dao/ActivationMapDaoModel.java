package com.trc.dao;

import java.util.List;

import com.trc.util.logger.activation.ActivationMap;

public interface ActivationMapDaoModel {

  public abstract List<ActivationMap> getByUserId(Integer userId);

}