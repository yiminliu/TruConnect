package com.trc.user;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * Unimplemented. This class was meant to generate user IDs so that a "sequence"
 * could be used rather than relying on MySQL auto-increment for creating user
 * ID.
 * 
 * @author Tachikoma
 * 
 */
public class UserIdGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
    // TODO Auto-generated method stub
    return null;
  }

}
