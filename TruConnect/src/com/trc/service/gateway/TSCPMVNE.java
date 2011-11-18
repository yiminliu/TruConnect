package com.trc.service.gateway;

public class TSCPMVNE {
	public static final String serviceName = "TruConnectService";
	public static final String namespace = "http://mvne.tscp.com/";

	public static class Location {
		public static final String localhost = "http://localhost:8080/TSCPMVNE/TruConnectService?wsdl";
		public static final String development = "http://USCAEL010-VM1:8080/TSCPMVNE/TruConnectService?wsdl";
		public static final String developmentIP = "http://10.10.30.188:8080/TSCPMVNE/TruConnectService?wsdl";
		public static final String production = "http://USCAEL010-VM3:8080/TSCPMVNE/TruConnectService?wsdl";
		public static final String productionIP = "http://10.10.30.190:8080/TSCPMVNE/TruConnectService?wsdl";
		@Deprecated
		public static final String oldDevelopment = "http://uscael004:8080/TSCPMVNE/TruConnectService?wsdl";
		@Deprecated
		public static final String oldDevelopmentIP = "http://10.10.30.62:8080/TSCPMVNE/TruConnectService?wsdl";
		@Deprecated
		public static final String oldProduction = "http://uscael001-vm5:8080/TSCPMVNE/TruConnectService?wsdl";
		@Deprecated
		public static final String oldProductionIP = "http://10.10.30.61:8080/TSCPMVNE/TruConnectService?wsdl";
	}
}
