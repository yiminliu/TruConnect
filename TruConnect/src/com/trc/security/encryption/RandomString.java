package com.trc.security.encryption;

import java.util.Random;

/**
 * Generates a random string of characters and numbers. Typically used to
 * generate new passwords.
 * 
 * @author Tachikoma
 * 
 */
public class RandomString {

  private static final char[] symbols = new char[36];

  static {
    for (int i = 0; i < 10; ++i)
      symbols[i] = (char) ('0' + i);
    for (int i = 10; i < 36; ++i)
      symbols[i] = (char) ('a' + i - 10);
  }

  private final Random random = new Random();

  private final char[] buf;

  public RandomString(int length) {
    if (length < 1)
      throw new IllegalArgumentException("length < 1: " + length);
    buf = new char[length];
  }

  public String nextString() {
    for (int i = 0; i < buf.length; ++i)
      buf[i] = symbols[random.nextInt(symbols.length)];
    return new String(buf);
  }

}
