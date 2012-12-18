package com.trc.util;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

public class DateUtil {

  public static XMLGregorianCalendar toXMLCal() throws DatatypeConfigurationException {
    return toXMLCal(new DateTime());
  }

  public static XMLGregorianCalendar toXMLCal(DateTime dateTime) throws DatatypeConfigurationException {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(dateTime.toDate());
    XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    return xmlCal;
  }
}
