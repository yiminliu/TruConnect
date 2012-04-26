package com.trc.user.activation.logger;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ldap.userdetails.Person;
import org.springframework.stereotype.Component;

@Component
public class ActStateFactory implements FactoryBean<ActState> {

  public ActState getObject() throws Exception {
    return ActState.ROOT;
  }

  public Class<?> getObjectType() {
    return Person.class;
  }

  public boolean isSingleton() {
    return true;
  }
}