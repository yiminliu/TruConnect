package com.trc.manager.impl;

import com.tscp.mvne.Contact;
import com.tscp.mvne.ContactInfo;
import com.tscp.mvne.CustomerAddress;

/**
 * This class is temporarily used to convert contact classes. These classes
 * should be unified into one class to be used across all applications.
 * 
 * @author Tachikoma
 * 
 */
public class ActivationUtil {

  public static final ContactInfo getContactInfo(int userId, com.trc.user.contact.ContactInfo inContactInfo) {
    ContactInfo contactInfo = new ContactInfo();
    contactInfo.setContact(getContact(userId, inContactInfo));
    contactInfo.setAddress(getAddress(inContactInfo.getAddress()));
    return contactInfo;
  }

  public static final Contact getContact(int userId, com.trc.user.contact.ContactInfo contactInfo) {
    Contact contact = new Contact();
    contact.setCustId(userId);
    contact.setEmail(contactInfo.getEmail());
    contact.setFirstName(contactInfo.getFirstName());
    contact.setLastName(contactInfo.getLastName());
    contact.setPhoneNumber(contactInfo.getPhoneNumber());
    return contact;
  }

  public static final CustomerAddress getAddress(com.trc.user.contact.Address inAddress) {
    CustomerAddress address = new CustomerAddress();
    address.setAddress1(inAddress.getAddress1());
    address.setAddress2(inAddress.getAddress2());
    address.setAddress3(inAddress.getAddress3());
    address.setCity(inAddress.getCity());
    address.setLabel(inAddress.getLabel());
    address.setState(inAddress.getState());
    address.setZip(inAddress.getZip());
    address.setAddressId(inAddress.getAddressId());
    return address;
  }

}
