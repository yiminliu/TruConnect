package com.trc.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.trc.dao.AuthorityDaoModel;
import com.trc.user.authority.Authority;

@Repository
@SuppressWarnings("unchecked")
public class AuthorityDao extends AbstractHibernateDao<Authority> implements AuthorityDaoModel {

  public AuthorityDao() {
    setClazz(Authority.class);
  }

  @Override
  public List<Authority> getAuthoritiesWithRole(String role) {
    Query query = getCurrentSession().createQuery("from Authority where authority = :authority");
    query.setString("authority", role);
    return query.list();
  }

  @Override
  public List<Authority> getAuthoritiesForUser(int userId) {
    Query query = getCurrentSession().createQuery("from Authority where userId = :userId");
    query.setInteger("userId", userId);
    return query.list();
  }

}
