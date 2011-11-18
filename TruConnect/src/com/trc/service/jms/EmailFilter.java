package com.trc.service.jms;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

public final class EmailFilter {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		EmailFilter emailFilter = new EmailFilter();
		emailFilter.init();
	}

	public static String smtpHost;
	static String inputPropertyFile = "com/tscp/mvne/connection/connection.properties";
	Properties props;

	InternetAddress from;
	ArrayList<InternetAddress> bccList = new ArrayList<InternetAddress>();

	public static String truconnectManageSite;
	public static String truconnectSupportSite;
	public static String truconnectTermsAndConditionsSite;
	public static String notificationBccList;
	public static String notificationExclusionList;

	public void init() {
		ClassLoader cl = EmailFilter.class.getClassLoader();
		System.out.println("Loading Email Properties file...");
		InputStream in = cl.getResourceAsStream(inputPropertyFile);
		props = new Properties();
		try {
			props.load(in);

			smtpHost = props.getProperty("email.smtphost");
			System.out.println("Setting SMTP Host to " + smtpHost);
			truconnectManageSite = props.getProperty("truconnect.manage.site");
			truconnectSupportSite = props.getProperty("truconnect.support.site");
			truconnectTermsAndConditionsSite = props.getProperty("truconnect.terms.and.conditions.site");
			notificationBccList = props.getProperty("email.notification.bccList");
			notificationExclusionList = props.getProperty("email.notification.exclusionList");
			System.out.println("bccList: " + notificationBccList);
			System.out.println("exlusionList: " + notificationExclusionList + "\n");
			// subjectAppender = props.getProperty("subject.appender");

			/*
			 * 2011-10-25 Commented out by JPONG - InternetAddress address = new
			 * InternetAddress("dta@telscape.net", "Dan Ta"); bccList = new
			 * ArrayList<InternetAddress>(); bccList.add(address); address = new
			 * InternetAddress("peter.maas@truconnect.com", "Peter Maas");
			 * bccList.add(address); address = new
			 * InternetAddress("jholop@telscape.net","Joseph Holop");
			 * bccList.add(address);
			 */

			/*
			 * 2011-10-25 JPONG - Iterate through the BCC list in the properties file
			 * and add them to the inclusionList. Then iterate through the exclusion
			 * list from the properties file and add them to the exclusionList. This
			 * list will be filtered to create the BCC list. This is necessary because
			 * the BCC list in the email object is set in various places (TSCPMVNE,
			 * TCBU etc.)
			 */
			bccList.add(new InternetAddress("jholop@telscape.net", "Josolop"));
			List<InternetAddress> inclusionList = getInternetAddresses(notificationBccList);
			List<InternetAddress> exclusionList = getInternetAddresses(notificationExclusionList);

			EmailFilter.include(bccList, inclusionList);
			System.out.println(bccList);
			EmailFilter.exclude(bccList, exclusionList);
			System.out.println(bccList);

			from = new InternetAddress("no-reply@truconnect.com", "TruConnect");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	};

	public static final void exclude(List<InternetAddress> addressList, List<InternetAddress> exclusionList) {
		// Collection<InternetAddress> toExclude = new ArrayList<InternetAddress>();
		// for (InternetAddress internetAddress : addressList) {
		// for (InternetAddress excludedAddress : exclusionList) {
		// if (matches(internetAddress, excludedAddress)) {
		// toExclude.add(internetAddress);
		// }
		// }
		// }
		// addressList.removeAll(toExclude);
		addressList.removeAll(exclusionList);
	}

	public static final void include(List<InternetAddress> addressList, List<InternetAddress> inclusionList) {
		// boolean included = false;
		// for (InternetAddress includedAddress : inclusionList) {
		// for (InternetAddress internetAddress : addressList) {
		// if (matches(includedAddress, internetAddress)) {
		// included = true;
		// }
		// }
		// if (!included) {
		// addressList.add(includedAddress);
		// }
		// }
		addressList.addAll(inclusionList);
	}

	public static final boolean matches(InternetAddress address1, InternetAddress address2) {
		// return address1.getAddress().equals(address2.getAddress());
		return address1.equals(address2);
	}

	public static final List<InternetAddress> getInternetAddresses(String addressListString)
			throws UnsupportedEncodingException {
		List<InternetAddress> resultList = new ArrayList<InternetAddress>();
		String[] addressListArray = addressListString.split(";");
		String[] address;
		for (String addressString : addressListArray) {
			address = addressString.split("\\|");
			if (address != null && address.length == 2) {
				resultList.add(new InternetAddress(address[0], address[1]));
			}
		}
		return resultList;
	}
}