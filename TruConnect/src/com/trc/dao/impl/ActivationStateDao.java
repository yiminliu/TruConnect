package com.trc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.trc.dao.ActivationStateDaoModel;
import com.trc.util.logger.activation.ActState;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;
import com.trc.util.logger.activation.ActivationStateId;

@Repository
public class ActivationStateDao extends AbstractHibernateDao<ActivationState> implements ActivationStateDaoModel {

  public ActivationStateDao() {
    setClazz(ActivationState.class);
  }

  @Override
  @Transactional
  public ActivationState getById(ActivationStateId activationStateId) {
    Preconditions.checkArgument(activationStateId != null);
    return (ActivationState) this.getCurrentSession().get(ActivationState.class, activationStateId);
  }

  @Override
  @Transactional
  public ActivationState getState(ActivationMap activationMap, ActState actState) {
    for (ActivationState rState : activationMap.getStates()) {
      if (rState.getState().equals(actState)) {
        return rState;
      }
    }
    return null;
  }

}
