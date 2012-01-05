package com.trc.service.gateway;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.Config;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.TruConnect;
import com.tscp.mvne.TruConnectService;

@Service
public class TruConnectGateway {
  @Autowired
  private Config config;
  private TruConnectService service;
  private TruConnect port;
  @Autowired
  private DevLogger devLogger;

  @PostConstruct
  public void init() {
    try {
      String namespace = config.getTSCPMVNE().getNamespace();
      String servicename = config.getTSCPMVNE().getServiceName();
      String location = config.getTSCPMVNE().getLocation();
      service = new TruConnectService(new URL(location), new QName(namespace, servicename));
    } catch (Exception e) {
      e.printStackTrace();
      service = new TruConnectService();
    }
    port = service.getTruConnectPort();
  }

  public TruConnect getPort() {
    return port;
  }
}