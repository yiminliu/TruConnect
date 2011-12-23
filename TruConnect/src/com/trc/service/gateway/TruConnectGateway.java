package com.trc.service.gateway;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;

import com.tscp.mvne.TruConnect;
import com.tscp.mvne.TruConnectService;

@Service
public class TruConnectGateway {
	private TruConnectService service;
	private TruConnect port;

	@PostConstruct
	public void init() {
		try {
			String namespace = TSCPMVNE.namespace;
			String servicename = TSCPMVNE.serviceName;
			//String location = "http://10.10.30.190:8080/TSCPMVNE_prod_1.9.3.6/TruConnectService?wsdl";
			String location = TSCPMVNE.Location.productionIP;
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