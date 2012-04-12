package com.trc.user.authority;

import java.io.Serializable;

import com.trc.user.User;

public class AuthorityId implements Serializable {
  private static final long serialVersionUID = 7740332242823859489L;
  private User user;
  private ROLE role;

  protected AuthorityId() {
    // do nothing
  }

  public AuthorityId(User user, ROLE role) {
    this.user = user;
    this.role = role;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public ROLE getRole() {
    return this.role;
  }

  public void setRole(ROLE role) {
    this.role = role;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((role == null) ? 0 : role.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AuthorityId other = (AuthorityId) obj;
    if (role == null) {
      if (other.role != null)
        return false;
    } else if (!role.equals(other.role))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }
}