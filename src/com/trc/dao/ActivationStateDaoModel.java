package com.trc.dao;

import com.trc.user.activation.logger.ActivationMap;
import com.trc.user.activation.logger.ActivationState;
import com.trc.user.activation.logger.ActivationStateId;

public interface ActivationStateDaoModel {

  public int saveActivationMap(ActivationMap activationMap);

  public void updateActivationMap(ActivationMap activationMap);

  public ActivationMap getActivationMap(int activationId);

  public void saveActivationState(ActivationState activationState);

  public void updateActivationState(ActivationState activationState);

  public ActivationState getActivationState(ActivationStateId activationStateId);

}
