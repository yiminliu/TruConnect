package com.trc.service.gateway;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.trc.config.CONFIG;
import com.tscp.mvne.TruConnect;
import com.tscp.mvne.TruConnectService;

@Service
public class TruConnectGateway {
  private static final Logger logger = LoggerFactory.getLogger("truconnect");
  private TruConnect port;
  private TruConnectService service;

  public TruConnect getPort() {
    return port;
  }

  @PostConstruct
  public void init() {
    try {
      if (!TSCPMVNE.initialized)
        CONFIG.loadProperties();
      service = new TruConnectService(new URL(TSCPMVNE.location), new QName(TSCPMVNE.namespace, TSCPMVNE.serviceName));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Error initializing TSCPMVNE webservice.", e);
      service = new TruConnectService();
    }
    port = service.getTruConnectPort();
  }
}