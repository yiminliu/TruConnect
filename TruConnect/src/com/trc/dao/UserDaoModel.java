package com.trc.dao;

import java.util.Collection;

import org.joda.time.DateTime;

import com.trc.user.User;

public interface UserDaoModel {

  public void enableUser(User user);

  public void disableUser(User user);

  public User getByEmail(String email);

  public User getByUsername(String username);

  public Collection<User> getByDate(DateTime startDate, DateTime endDate);

  public Collection<User> getAllWithRole(String role);

  public Collection<User> search(String param);

  public Collection<User> searchByEmail(String email);

  public Collection<User> searchByUsername(String username);

  public Collection<User> searchByEmailAndDate(String email, DateTime startDate, DateTime endDate);

  public Collection<User> searchByNotEmailAndDate(String email, DateTime startDate, DateTime endDate);

}
