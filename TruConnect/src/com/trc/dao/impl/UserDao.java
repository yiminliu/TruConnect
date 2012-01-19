package com.trc.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.trc.dao.UserDaoModel;
import com.trc.user.User;
import com.trc.user.authority.Authority;

@Repository
@SuppressWarnings("unchecked")
public class UserDao extends AbstractHibernateDao<User> implements UserDaoModel {

  public UserDao() {
    setClazz(User.class);
  }

  @Override
  public void enableUser(User user) {
    user.setEnabled(true);
    user.setDateEnabled(new DateTime().toDate());
    merge(user);
  }

  @Override
  public void disableUser(User user) {
    user.setEnabled(false);
    user.setDateDisabled(new DateTime().toDate());
    merge(user);
  }

  @Override
  public User getByUsername(String username) {
    Query query = getCurrentSession().createQuery("from User where username = :username");
    query.setString("username", username);
    List<User> results = query.list();
    if (isUniqueResult(results)) {
      return getUniqueResult(results);
    } else {
      return null;
    }
  }

  @Override
  public User getByEmail(String email) {
    Query query = getCurrentSession().createQuery("from User where email = :email");
    query.setString("email", email);
    List<User> results = query.list();
    if (isUniqueResult(results)) {
      return getUniqueResult(results);
    } else {
      return null;
    }
  }

  @Override
  public List<User> getByDate(DateTime startDate, DateTime endDate) {
    Query query = getCurrentSession().createQuery("from User where dateEnabled between :startDate and :endDate");
    query.setDate("startDate", startDate.toDate()).setDate("endDate", endDate.toDate());
    return query.list();
  }

  @Override
  public List<User> getAllWithRole(String role) {
    Criteria criteria = getCurrentSession().createCriteria(Authority.class);
    criteria.add(Property.forName("authority").eq(role));
    criteria.setProjection(Projections.property("user"));
    return criteria.list();
  }

  @Override
  public List<User> search(String param) {
    Query query = getCurrentSession().createQuery("from User where username like :username or email like :email");
    query.setString("username", wildcard(param));
    query.setString("email", wildcard(param));
    List<User> results = query.list();
    return results;
  }

  @Override
  public List<User> searchByUsername(String username) {
    Query query = getCurrentSession().createQuery("from User where username like :username");
    query.setString("username", wildcard(username));
    return query.list();
  }

  @Override
  public List<User> searchByEmail(String email) {
    Query query = getCurrentSession().createQuery("from User where email like :email");
    query.setString("email", wildcard(email));
    return query.list();
  }

  @Override
  public List<User> searchByEmailAndDate(String email, DateTime startDate, DateTime endDate) {
    endDate.plusDays(1);
    Query query = getCurrentSession().createQuery(
        "from User where email like :email and dateEnabled between :startDate and :endDate");
    query.setString("email", wildcard(email));
    query.setDate("startDate", startDate.toDate());
    query.setDate("endDate", endDate.toDate());
    return query.list();
  }

  @Override
  public List<User> searchByNotEmailAndDate(String email, DateTime startDate, DateTime endDate) {
    Query query = getCurrentSession().createQuery(
        "from User where email not like :email and dateEnabled between :startDate and :endDate");
    query.setString("email", wildcard(email));
    query.setDate("startDate", startDate.toDate());
    query.setDate("endDate", endDate.toDate());
    return query.list();
  }

  @Deprecated
  public List<User> searchByEmailCriteria(String email) {
    Criteria authCriteria = getCurrentSession().createCriteria(Authority.class);
    authCriteria.add(Restrictions.eq("authority", "ROLE_USER"));
    authCriteria.setProjection(Projections.property("user.userId"));

    Criteria userCriteria = getCurrentSession().createCriteria(User.class);
    userCriteria.add(Property.forName("email").like(email, MatchMode.ANYWHERE));
    userCriteria.add(Property.forName("userId").in(authCriteria.list()));

    return userCriteria.list();

  }

}