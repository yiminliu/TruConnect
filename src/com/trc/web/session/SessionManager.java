package com.trc.web.session;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.trc.security.encryption.StringEncrypter;

public final class SessionManager {

  public static final String lookupKey(SessionRequest sessionRequest) {
    return getCurrentSession().getId() + "_" + sessionRequest;
  }

  public static final HttpSession getCurrentSession() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest().getSession(true);
  }

  public static final Object get(SessionRequest sessionRequest) {
    return get(lookupKey(sessionRequest));
  }

  public static final Object get(String key) {
    HttpSession session = getCurrentSession();
    Object mutex = WebUtils.getSessionMutex(session);
    synchronized (mutex) {
      return session.getAttribute(key);
    }
  }

  public static final void set(SessionRequest sessionRequest, Object obj) {
    set(lookupKey(sessionRequest), obj);
  }

  public static final void set(String key, Object obj) {
    HttpSession session = getCurrentSession();
    Object mutex = WebUtils.getSessionMutex(session);
    synchronized (mutex) {
      session.setAttribute(key, obj);
    }
  }

  public static final void clear(String key) {
    HttpSession session = getCurrentSession();
    Object mutex = WebUtils.getSessionMutex(session);
    synchronized (mutex) {
      session.removeAttribute(key);
    }
  }

  public static final SessionToken createToken(SessionRequest sessionRequest, String description) {
    SessionToken sessionToken = new SessionToken(sessionRequest, description);
    set(sessionRequest, sessionToken);
    return sessionToken;
  }

  public static final SessionToken fetchToken(SessionRequest sessionRequest) {
    Object obj = get(sessionRequest);
    return obj instanceof SessionToken ? (SessionToken) obj : null;
  }

  public static final void consumeToken(SessionToken token) {
    clear(token.getId());
  }

  public static final StringEncrypter getEncrypter() {
    StringEncrypter stringEncrypter = (StringEncrypter) SessionManager.get(SessionRequest.ENCRYPTER);
    if (stringEncrypter == null) {
      stringEncrypter = new StringEncrypter(SessionManager.getCurrentSession().getId());
      SessionManager.set(SessionRequest.ENCRYPTER, stringEncrypter);
    }
    return stringEncrypter;
  }
}
