package com.trc.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.trc.security.encryption.Encrypter;
import com.trc.web.session.SessionManager;

public abstract class EncryptedController {

  public static Encrypter getEncrypter() {
    return SessionManager.getEncrypter();
  }

  public static String encryptId(int input) {
    try {
      return getEncrypter().encryptIntUrlSafe(input);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static int decryptId(String input) {
    try {
      return getEncrypter().decryptIntUrlSafe(input);
    } catch (NumberFormatException e) {
      // System.out.println("EncryptedController.decryptId() NumberFormatException: "
      // + e.getMessage());
      return -1;
    } catch (IOException e) {
      // System.out.println("EncryptedController.decryptId() IOException: " +
      // e.getMessage());
      return -1;
    }
  }
}
