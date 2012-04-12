package com.trc.user.authority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trc.user.User;

@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
public class Authority implements Serializable {
  private static final long serialVersionUID = -6398259652301626438L;
  private User user;
  private ROLE role;

  protected Authority() {
    // default
  }

  public Authority(ROLE role) {
    this.role = role;
  }

  public Authority(User user, ROLE role) {
    this.user = user;
    this.role = role;
  }

  @Id
  @Column(name = "authority")
  @Enumerated(EnumType.STRING)
  public ROLE getRole() {
    return this.role;
  }

  public void setRole(ROLE role) {
    this.role = role;
  }

  @Id
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, insertable = false)
  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Authority [Role=" + role + "]";
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
    Authority other = (Authority) obj;
    if (role == null) {
      if (other.role != null)
        return false;
    } else if (!role.equals(other.role))
      return false;
    return true;
  }

}