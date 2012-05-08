package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.dao.impl.ActivationStateDao;
import com.trc.user.activation.logger.ActState;
import com.trc.user.activation.logger.ActivationMap;
import com.trc.user.activation.logger.ActivationState;
import com.trc.user.activation.logger.ActivationStateId;

@Component
public class ActivationStateManager {
  @Autowired
  private ActivationStateDao activationStateDao;

  public ActivationMap getActivationMap(int actId) {
    return activationStateDao.getActivationMap(actId);
  }

  public List<ActivationMap> getActivationMapByUserId(int userId) {
    return activationStateDao.getRegistrationMapByUserId(userId);
  }

  public ActivationState getActivationState(ActivationMap actMap, ActState state) {
    ActivationStateId actStateId = new ActivationStateId();
    actStateId.setActivationMap(actMap);
    actStateId.setActState(state);
    return activationStateDao.getActivationState(actStateId);
  }

}
