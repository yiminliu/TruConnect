package com.trc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.dao.ActivationStateDaoModel;
import com.trc.user.activation.logger.ActState;
import com.trc.user.activation.logger.ActivationMap;
import com.trc.user.activation.logger.ActivationState;
import com.trc.user.activation.logger.ActivationStateId;

@Repository
public class ActivationStateDao extends HibernateDaoSupport implements ActivationStateDaoModel {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  @Override
  public int saveActivationMap(ActivationMap activationMap) {
    return (Integer) getHibernateTemplate().save(activationMap);
  }

  @Override
  public void updateActivationMap(ActivationMap activationMap) {
    getHibernateTemplate().update(activationMap);
  }

  @Override
  public void saveActivationState(ActivationState activationState) {
    getHibernateTemplate().save(activationState);
  }

  @Override
  public void updateActivationState(ActivationState activationState) {
    getHibernateTemplate().update(activationState);
  }

  @Override
  public ActivationState getActivationState(ActivationStateId activationStateId) {
    return getHibernateTemplate().get(ActivationState.class, activationStateId);
  }

  @Override
  public ActivationMap getActivationMap(int activationId) {
    return getHibernateTemplate().get(ActivationMap.class, activationId);
  }

  public ActivationState getRegistrationState(ActivationMap activationMap, ActState actState) {
    for (ActivationState rState : activationMap.getStates()) {
      if (rState.getState().equals(actState)) {
        return rState;
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public List<ActivationMap> getRegistrationMapByUserId(int userId) {
    return getHibernateTemplate().find("from ActivationMap activationMap where activationMap.user.userId = ?", userId);
  }
}
