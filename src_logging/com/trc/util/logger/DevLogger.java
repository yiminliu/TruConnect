package com.trc.util.logger;

import org.slf4j.LoggerFactory;

public class DevLogger {
  private static org.slf4j.Logger logger = LoggerFactory.getLogger("devLogger");

  public static void log(String message, Object... args) {
    logger.debug(message, args);
  }

}