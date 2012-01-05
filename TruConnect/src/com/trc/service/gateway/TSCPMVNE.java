package com.trc.service.gateway;

public class TSCPMVNE {
  private static String serviceName = "TruConnectService";
  private static String namespace = "http://mvne.tscp.com/";
  private static String location = "http://10.10.30.188:8080/TSCPMVNE/TruConnectService?wsdl";

  public TSCPMVNE(String serviceName, String namespace, String location) {
    this.serviceName = serviceName;
    this.namespace = namespace;
    this.location = location;
  }

  public static String getServiceName() {
    return serviceName;
  }

  public static String getNamespace() {
    return namespace;
  }

  public static String getLocation() {
    return location;
  }

}
