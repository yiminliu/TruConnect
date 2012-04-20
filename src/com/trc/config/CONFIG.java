package com.trc.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.trc.exception.InitializationException;
import com.trc.service.gateway.TSCPMVNE;
import com.trc.util.logger.DevLogger;

@Component
public final class CONFIG {
  private static ClassLoader classLoader;
  public static String ENVIRONMENT;
  public static boolean ADMIN;
  private static final String configFile = "config/config.properties";
  private static final String monthsFile = "config/dates/months.properties";
  private static final String yearsFile = "config/dates/years.properties";
  private static final String statesFile = "config/geo/states.properties";
  public static SortedMap<String, String> states = new TreeMap<String, String>();
  public static SortedMap<String, String> months = new TreeMap<String, String>();
  public static SortedMap<Integer, String> yearsFuture = new TreeMap<Integer, String>();
  public static SortedMap<Integer, String> yearsPast = new TreeMap<Integer, String>();
  public static SortedMap<Integer, String> years = new TreeMap<Integer, String>();

  public static Set<String> validEnvironments = new HashSet<String>();
  static {
    validEnvironments.add("local");
    validEnvironments.add("dev");
    validEnvironments.add("pretest");
    validEnvironments.add("production");
  }

  public static boolean initialized = false;

  private static org.slf4j.Logger logger = LoggerFactory.getLogger(CONFIG.class);

  @PostConstruct
  public static void loadProperties() {
    if (!initialized) {
      classLoader = CONFIG.class.getClassLoader();
      try {
        loadConfig();
        loadMonths();
        loadYears();
        loadStates();
        initialized = true;
      } catch (IOException e) {
        logger.error("Failed to load properties file: " + e.getMessage());
      } catch (InitializationException e) {
        logger.error("Failed to initialize. {}", e);
      }
    }
  }

  private static void loadConfig() throws IOException, InitializationException {
    if (!TSCPMVNE.initialized) {
      Properties props = new Properties();
      props.load(classLoader.getResourceAsStream(configFile));
      TSCPMVNE.serviceName = props.getProperty("serviceName");
      TSCPMVNE.namespace = props.getProperty("namespace");
      ADMIN = props.getProperty("admin").equals("0") ? false : true;
      ENVIRONMENT = props.getProperty("environment");
      if (validEnvironments.contains(ENVIRONMENT)) {
        if (ENVIRONMENT.equals("local")) {
          TSCPMVNE.location = props.getProperty("wsdl_localhost");
        } else if (ENVIRONMENT.equals("dev")) {
          TSCPMVNE.location = props.getProperty("wsdl_development");
        } else if (ENVIRONMENT.equals("pretest")) {
          TSCPMVNE.location = props.getProperty("wsdl_pretest");
        } else if (ENVIRONMENT.equals("production")) {
          TSCPMVNE.location = props.getProperty("wsdl_production");
        }
        if (TSCPMVNE.location == null) {
          throw new InitializationException("Unable to find value in config.properties for environment " + ENVIRONMENT);
        }
      } else {
        throw new InitializationException(ENVIRONMENT + " is not a valid setting in config.properties");
      }
      TSCPMVNE.initialized = true;
      DevLogger.debug("TSCPMVNE location set to " + TSCPMVNE.location);
    }
  }

  private static void loadMonths() throws IOException {
    Properties properties = new Properties();
    properties.load(classLoader.getResourceAsStream(monthsFile));
    for (Entry<Object, Object> entry : properties.entrySet()) {
      months.put((String) entry.getKey(), (String) entry.getValue());
    }
  }

  private static void loadYears() throws IOException {
    Properties properties = new Properties();
    properties.load(classLoader.getResourceAsStream(yearsFile));
    int futureRange = Integer.parseInt(properties.getProperty("futureRange", "10"));
    int pastRange = Integer.parseInt(properties.getProperty("pastRange", "4"));
    int currentYear = new DateTime().getYear();
    for (int i = 0; i < futureRange; i++) {
      yearsFuture.put(currentYear + i, Integer.toString(currentYear + i).substring(2));
    }
    for (int i = 0; i < pastRange; i++) {
      yearsPast.put(currentYear - i, Integer.toString(currentYear - i).substring(2));
    }
    years.putAll(yearsPast);
    years.putAll(yearsFuture);
  }

  private static void loadStates() throws IOException {
    Properties properties = new Properties();
    properties.load(classLoader.getResourceAsStream(statesFile));
    for (Entry<Object, Object> entry : properties.entrySet()) {
      states.put((String) entry.getKey(), (String) entry.getValue());
    }
  }

  // public static SortedMap<Integer, String> getYearsFuture() {
  // return yearsFuture;
  // }
  //
  // public static void setYearsFuture(SortedMap<Integer, String> yearsFuture) {
  // CONFIG.yearsFuture = yearsFuture;
  // }
  //
  // public static SortedMap<Integer, String> getYearsPast() {
  // return yearsPast;
  // }
  //
  // public static void setYearsPast(SortedMap<Integer, String> yearsPast) {
  // CONFIG.yearsPast = yearsPast;
  // }
  //
  // public static SortedMap<String, String> getStates() {
  // return states;
  // }
  //
  // public static void setStates(SortedMap<String, String> states) {
  // CONFIG.states = states;
  // }
  //
  // public static SortedMap<String, String> getMonths() {
  // return months;
  // }
  //
  // public static void setMonths(SortedMap<String, String> months) {
  // CONFIG.months = months;
  // }

}