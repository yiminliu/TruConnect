package com.trc.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.trc.dao.AbstractHibernateDaoModel;

@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T extends Serializable> implements AbstractHibernateDaoModel<T> {
  private Class<T> clazz;

  @Autowired
  SessionFactory sessionFactory;

  @Override
  public void setClazz(final Class<T> clazzToSet) {
    this.clazz = clazzToSet;
  }

  @Override
  @Transactional
  public T getById(final Integer id) {
    Preconditions.checkArgument(id != null);
    return (T) this.getCurrentSession().get(this.clazz, id);
  }

  @Override
  @Transactional
  public List<T> getAll() {
    return this.getCurrentSession().createQuery("from " + this.clazz.getName()).list();
  }

  @Override
  @Transactional
  public void persist(final T entity) {
    Preconditions.checkNotNull(entity);
    this.getCurrentSession().persist(entity);
  }

  @Override
  @Transactional
  public T merge(final T entity) {
    Preconditions.checkNotNull(entity);
    return (T) this.getCurrentSession().merge(entity);
  }

  @Override
  @Transactional
  public Serializable save(final T entity) {
    Preconditions.checkNotNull(entity);
    return this.getCurrentSession().save(entity);
  }

  @Override
  @Transactional
  public void update(final T entity) {
    Preconditions.checkNotNull(entity);
    this.getCurrentSession().update(entity);
  }

  @Override
  @Transactional
  public void delete(final T entity) {
    Preconditions.checkNotNull(entity);
    this.getCurrentSession().delete(entity);
  }

  @Override
  @Transactional
  public void deleteById(final Integer entityId) {
    final T entity = this.getById(entityId);
    Preconditions.checkState(entity != null);
    this.delete(entity);
  }

  protected final Session getCurrentSession() {
    return this.sessionFactory.getCurrentSession();
  }

  protected final boolean isUniqueResult(Collection<T> results) {
    return results != null && results.size() == 1;
  }

  protected final T getUniqueResult(List<T> results) {
    return results.get(0);
  }

  protected final String wildcard(String param) {
    return "%" + param + "%";
  }
}