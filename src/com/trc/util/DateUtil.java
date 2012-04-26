package com.trc.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

public class DateUtil {

  public static XMLGregorianCalendar toXMLCal() throws DatatypeConfigurationException {
    return toXMLCal(new Date());
  }

  public static XMLGregorianCalendar toXMLCal(Date date) throws DatatypeConfigurationException {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    return xmlCal;
  }

  public static XMLGregorianCalendar toXMLCal(DateTime dateTime) throws DatatypeConfigurationException {
    return toXMLCal(dateTime.toDate());
  }
}
