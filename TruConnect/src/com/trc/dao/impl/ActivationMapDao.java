package com.trc.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.trc.dao.ActivationMapDaoModel;
import com.trc.util.logger.activation.ActivationMap;

@Repository
public class ActivationMapDao extends AbstractHibernateDao<ActivationMap> implements ActivationMapDaoModel {

  public ActivationMapDao() {
    setClazz(ActivationMap.class);
  }

  /* (non-Javadoc)
   * @see com.trc.dao.impl.ActivationMapDaoModel#getByUserId(java.lang.Integer)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<ActivationMap> getByUserId(Integer userId) {
    Preconditions.checkArgument(userId != null);
    Query query = this.getCurrentSession().createQuery("from ActivationMap am where am.user.userId = :userId");
    query.setInteger("userId", userId);
    return query.list();
  }

}
