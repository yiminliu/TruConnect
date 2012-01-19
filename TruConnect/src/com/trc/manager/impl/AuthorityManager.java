package com.trc.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.dao.impl.AuthorityDao;
import com.trc.manager.AuthorityManagerModel;
import com.trc.user.authority.Authority;

@Service
public class AuthorityManager implements AuthorityManagerModel {
  @Autowired
  private AuthorityDao authorityDao;

  @Override
  public void saveAuthority(Authority authority) {
    authorityDao.save(authority);
  }

  @Override
  public void deleteAuthority(Authority authority) {
    authorityDao.delete(authority);
  }

  @Override
  public void updateAuthority(Authority authority) {
    authorityDao.update(authority);
  }

  @Override
  public List<Authority> getAuthoritiesWithRole(String role) {
    return authorityDao.getAuthoritiesWithRole(role);
  }

  @Override
  public List<Authority> getAuthoritiesForUser(int userId) {
    return authorityDao.getAuthoritiesForUser(userId);
  }

}
