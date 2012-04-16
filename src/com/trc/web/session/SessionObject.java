package com.trc.web.session;

import java.io.Serializable;

public abstract class SessionObject implements Serializable {
  private static final long serialVersionUID = 7576622855714795486L;
  protected SessionToken sessionToken;

  public SessionToken getSessionToken() {
    return sessionToken;
  }

  public synchronized void setSessionToken(SessionToken sessionToken) {
    this.sessionToken = sessionToken;
  }

}
