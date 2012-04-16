package com.trc.web.session;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.trc.security.encryption.RandomString;
import com.trc.security.encryption.StringEncrypter;

public final class SessionManager {
  private static final Logger logger = LoggerFactory.getLogger("devLogger");

  public static final String lookupKey(SessionKey sessionKey) {
    String key = getCurrentSession().getId();
    switch (sessionKey) {
    case DEVICE_SWAP:
      key += "_DEVICE_SWAP";
      break;
    case DEVICE_RENAME:
      key += "_DEVICE_RENAME";
      break;
    case DEVICE_DEACTIVATE:
      key += "_DEVICE_DEACTIVATE";
      break;
    case DEVICE_REACTIVATE:
      key += "DEVICE_REACTIVATE";
      break;
    case DEVICE_ACCOUNT:
      key += "_DEVICE_ACCOUNT";
      break;
    case DEVICE_ACCESSFEEDATE:
      key += "_DEVICE_ACCESSFEEDATE";
      break;
    case DEVICE_ACCOUNTDETAIL:
      key += "_DEVICE_TOPUP";
      break;
    case CREDITCARD_UPDATE:
      key += "_CREDITCARD_UPDATE";
      break;
    case ENCRYPTER:
      key = "ENCRYPTER";
      break;
    default:
      key = null;
    }
    return key;
  }

  public static final HttpSession getCurrentSession() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest().getSession(true);
  }

  public static final Object get(SessionKey sessionKey) {
    return get(lookupKey(sessionKey));
  }

  public static final Object get(String key) {
    HttpSession session = getCurrentSession();
    Object mutex = WebUtils.getSessionMutex(session);
    synchronized (mutex) {
      return session.getAttribute(key);
    }
  }

  public static final void set(SessionKey sessionKey, Object obj) {
    set(lookupKey(sessionKey), obj);
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

  public static final SessionToken createToken(String description) {
    String id = RandomString.get();
    SessionToken sessionToken = new SessionToken(id, description);
    set(id, sessionToken);
    return sessionToken;
  }

  public static final SessionToken fetchToken(String tokenId) {
    logger.debug("fetchToken(id) called with {}", tokenId);
    Object obj = get(tokenId);
    return obj instanceof SessionToken ? (SessionToken) obj : null;
  }

  public static final SessionToken fetchToken(SessionObject sessionObject) {
    logger.debug("fetchToken(sessionObject) called with {} with token {}", sessionObject, sessionObject.getSessionToken());
    Object obj = get(sessionObject.getSessionToken().getId());
    return obj instanceof SessionToken ? (SessionToken) obj : null;
  }

  public static final void consumeToken(SessionToken token) {
    clear(token.getId());
  }

  public static final StringEncrypter getEncrypter() {
    StringEncrypter stringEncrypter = (StringEncrypter) SessionManager.get(SessionKey.ENCRYPTER);
    if (stringEncrypter == null) {
      stringEncrypter = new StringEncrypter(SessionManager.getCurrentSession().getId());
      SessionManager.set(SessionKey.ENCRYPTER, stringEncrypter);
    }
    return stringEncrypter;
  }
}
