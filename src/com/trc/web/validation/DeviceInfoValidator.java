package com.trc.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.manager.DeviceManager;
import com.tscp.mvne.Device;

@Component
public class DeviceInfoValidator implements Validator {
  @Autowired
  private DeviceManager deviceManager;

  public static final int MIN_LABEL_SIZE = 3;

  @Override
  public boolean supports(Class<?> myClass) {
    return Device.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Device deviceInfo = (Device) target;
    checkDeviceEsn(deviceInfo.getValue(), errors);
    checkDeviceLabel(deviceInfo.getLabel(), errors);
    checkDeviceAvailability(deviceInfo.getValue(), errors);
  }

  public void checkDeviceEsn(String esn, Errors errors) {
    if (ValidationUtil.isEmpty(esn)) {
      errors.rejectValue("value", "device.esn.required", "You must enter an ESN");
    } else if (!isDec(esn) && !isHex(esn)) {
      errors.rejectValue("value", "device.esn.invalid", "Invalid esn");
    }
  }

  public void checkDeviceLabel(String label, Errors errors) {
    if (ValidationUtil.isEmpty(label)) {
      errors.rejectValue("label", "device.label.required", "You must name your device");
    } else if (!ValidationUtil.isBetween(label, MIN_LABEL_SIZE, 100)) {
      errors.rejectValue("label", "device.label.size", "Device label must be at least 3 characters");
    }
  }

  public void checkDeviceAvailability(String esn, Errors errors) {
    if (!errors.hasErrors() && !ValidationUtil.isEmpty(esn) && !deviceManager.isDeviceAvailable(esn)) {
      String[] args = { esn };
      errors.rejectValue("value", "device.esn.unavailable", args, "The ESN you entered is already in use");
    }
  }

  private boolean isDec(String esn) {
    return ValidationUtil.isNumeric(esn) && (esn.length() == 11 || esn.length() == 18);
  }

  private boolean isHex(String esn) {
    return ValidationUtil.isAlphaNumeric(esn) && (esn.length() == 8 || esn.length() == 14);
  }

}