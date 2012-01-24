package com.trc.dao;

import java.util.Collection;

import com.trc.user.authority.Authority;

public interface AuthorityDaoModel {

  public Collection<Authority> getAuthoritiesWithRole(String role);

  public Collection<Authority> getAuthoritiesForUser(Integer userId);

}
