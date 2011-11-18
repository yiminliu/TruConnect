package com.trc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.util.logger.activation.ActState;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;
import com.trc.util.logger.activation.ActivationStateId;

@Repository
public class ActivationStateDao extends HibernateDaoSupport implements ActivationStateDaoModel {

	@Autowired
	public void init(HibernateTemplate hibernateTemplate) {
		setHibernateTemplate(hibernateTemplate);
	}

	@Override
	public int saveRegistrationMap(ActivationMap registrationMap) {
		return (Integer) getHibernateTemplate().save(registrationMap);
	}

	@Override
	public void updateRegistrationMap(ActivationMap registrationMap) {
		getHibernateTemplate().update(registrationMap);
	}

	@Override
	public void saveRegistrationState(ActivationState registrationState) {
		getHibernateTemplate().save(registrationState);
	}

	@Override
	public void updateRegistratonState(ActivationState registrationState) {
		getHibernateTemplate().update(registrationState);
	}

	@Override
	public ActivationState getRegistrationState(ActivationStateId registrationStateId) {
		return getHibernateTemplate().get(ActivationState.class, registrationStateId);
	}

	@Override
	public ActivationMap getRegistrationMap(int regId) {
		return getHibernateTemplate().get(ActivationMap.class, regId);
	}

	public ActivationState getRegistrationState(ActivationMap registrationMap, ActState regState) {
		for (ActivationState rState : registrationMap.getStates()) {
			if (rState.getState().equals(regState)) {
				return rState;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ActivationMap> getRegistrationMapByUserId(int userId) {
		return getHibernateTemplate().find("from RegistrationMap registrationMap where userId = ?", userId);
	}
}
