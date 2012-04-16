package com.trc.web.session;

import java.io.Serializable;

public class SessionToken implements Serializable {
  private static final long serialVersionUID = 2699326845796317792L;
  private String id;
  private String description;
  private boolean valid = true;

  public SessionToken() {
    // do nothing
  }

  public SessionToken(String id, String description) {
    this.id = id;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public synchronized void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public synchronized void setDescription(String description) {
    this.description = description;
  }

  public synchronized boolean isValid() {
    return valid;
  }

  public synchronized void setValid(boolean valid) {
    this.valid = valid;
  }

  public synchronized void consume() {
    this.valid = false;
    SessionManager.consumeToken(this);
  }

  @Override
  public String toString() {
    return "SessionToken [id=" + id + ", description=" + description + ", valid=" + valid + "]";
  }

}