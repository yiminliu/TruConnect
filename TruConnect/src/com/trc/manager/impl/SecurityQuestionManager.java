package com.trc.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trc.dao.impl.SecurityQuestionDao;
import com.trc.manager.SecurityQuestionManagerModel;
import com.trc.user.security.SecurityQuestion;

@Service
public class SecurityQuestionManager implements SecurityQuestionManagerModel {
  @Autowired
  private SecurityQuestionDao securityQuestionDao;

  @Override
  @Transactional
  public List<SecurityQuestion> getSecurityQuestions() {
    return securityQuestionDao.getAll();
  }

  @Override
  @Transactional
  public SecurityQuestion getSecurityQuestion(int id) {
    return securityQuestionDao.getById(id);
  }

}