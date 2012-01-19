package com.trc.dao.impl;

import org.springframework.stereotype.Repository;

import com.trc.dao.SecurityQuestionDaoModel;
import com.trc.user.security.SecurityQuestion;

@Repository
public class SecurityQuestionDao extends AbstractHibernateDao<SecurityQuestion> implements SecurityQuestionDaoModel {

  public SecurityQuestionDao() {
    setClazz(SecurityQuestion.class);
  }

}
