package com.trc.security.encryption;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trc.web.session.SessionManager;

public class SessionEncrypter {
  private static Logger logger = LoggerFactory.getLogger(SessionEncrypter.class);

  public static Encrypter getEncrypter() {
    return SessionManager.getEncrypter();
  }

  public static String encryptId(int input) {
    try {
      return getEncrypter().encryptIntUrlSafe(input);
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage(), e);
      return null;
    }
  }

  public static int decryptId(String input) {
    try {
      return getEncrypter().decryptIntUrlSafe(input);
    } catch (NumberFormatException e) {
      logger.error(e.getMessage(), e);
      return -1;
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      return -1;
    }
  }
}