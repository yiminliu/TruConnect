package com.trc.web.session;

import java.io.Serializable;

import com.trc.security.encryption.RandomString;

public class SessionToken implements Serializable {
  private static final long serialVersionUID = 2699326845796317792L;
  private SessionRequest sessionRequest;
  private String id = RandomString.get(8);
  private String description;
  private boolean valid;

  public SessionToken() {
    // do nothing
  }

  public SessionToken(SessionRequest sessionRequest, String description) {
    this.sessionRequest = sessionRequest;
    this.description = description;
    this.valid = true;
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

  public SessionRequest getRequest() {
    return sessionRequest;
  }

  public void setRequest(SessionRequest sessionRequest) {
    this.sessionRequest = sessionRequest;
  }

  public synchronized boolean isValid() {
    return valid;
  }

  public synchronized void setValid(boolean valid) {
    this.valid = valid;
  }

  public synchronized void consume() throws SessionTokenInvalidException {
    if (valid) {
      this.valid = false;
      SessionManager.consumeToken(this);
    } else {
      throw new SessionTokenInvalidException("REQUEST " + id + " is no longer valid or has already been consumed.");
    }
  }

  @Override
  public String toString() {
    return "SessionToken [sessionRequest=" + sessionRequest + ", id=" + id + ", description=" + description + ", valid=" + valid + "]";
  }

}