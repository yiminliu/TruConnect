package com.trc.dao;

import com.trc.util.logger.activation.ActState;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;
import com.trc.util.logger.activation.ActivationStateId;

public interface ActivationStateDaoModel {

  public ActivationState getById(ActivationStateId registrationStateId);

  public ActivationState getState(ActivationMap activationMap, ActState actState);

}
