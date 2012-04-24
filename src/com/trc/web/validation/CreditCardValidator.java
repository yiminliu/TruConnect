package com.trc.web.validation;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.contact.Address;
import com.trc.util.SimpleDate;
import com.tscp.mvne.CreditCard;

@Component
public class CreditCardValidator extends AddressValidator {
  public static final int americanExpress = 3;
  public static final int visa = 4;
  public static final int masterCard = 5;
  public static final int discover = 6;

  @Override
  public boolean supports(Class<?> myClass) {
    return CreditCard.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreditCard creditCard = (CreditCard) target;
    checkName(creditCard.getNameOnCreditCard(), errors);
    checkCreditCardNumber(creditCard.getCreditCardNumber(), errors);
    checkCvv(creditCard.getVerificationcode(), errors);
    checkExpirationDate(creditCard.getExpirationDate(), errors);
    checkAddress(creditCard, errors);
  }

  protected void checkName(String name, Errors errors) {
    if (ValidationUtil.isEmpty(name)) {
      errors.rejectValue("nameOnCreditCard", "creditCard.name.required", "You must enter the name on the card");
    } else if (!ValidationUtil.isBetween(name, 3, 100)) {
      errors.rejectValue("nameOnCreditCard", "creditCard.name.size", "Name must be at least 3 characters");
    }
  }

  protected void checkCreditCardNumber(String cardNumber, Errors errors) {
    if (cardNumber.toLowerCase().contains("x")) {
      return;
    } else if (ValidationUtil.isEmpty(cardNumber)) {
      errors.rejectValue("creditCardNumber", "creditCard.number.required", "You must enter a credit card number");
    } else if (!ValidationUtil.isBetween(cardNumber, 15, 16)) {
      errors.rejectValue("creditCardNumber", "creditCard.number.size", "You have entered an invalid credit card number");
    } else if (!mod10(cardNumber)) {
      errors.rejectValue("creditCardNumber", "creditCard.number.invalid", "You have entered an invalid credit card number");
    }
  }

  protected void checkCvv(String cvv, Errors errors) {
    if (ValidationUtil.isEmpty(cvv)) {
      errors.rejectValue("verificationcode", "creditCard.verificationCode.required", "You must enter a CVV number");
    }
  }

  protected void checkExpirationDate(String date, Errors errors) {
    if (!date.matches("\\d{4}")) {
      errors.rejectValue("expirationDate", "creditCard.expiration.invalid", "Invalid date");
    } else {
      int month = Integer.parseInt(date.substring(0, 2));
      if (month < 1 || month > 12) {
        errors.rejectValue("expirationDate", "creditCard.expiration.month.invalid", "Invalid date (month)");
      } else {
        try {
          String dateCeiling = padString(Integer.toString(month + 1)) + date.substring(2);
          Date expirationDate = SimpleDate.parseShortDate(dateCeiling);
          if (expirationDate.before(new Date())) {
            errors.rejectValue("expirationDate", "creditCard.expiration.expired", "Card is expired");
          }
        } catch (ParseException e) {
          e.printStackTrace();
          errors.rejectValue("expirationDate", "creditCard.expiration.invalid", "Invalid date");
        }
      }
    }
  }

  protected static String padString(String value) {
    while (value.length() < 2) {
      value = "0" + value;
    }
    return value;
  }

  protected void checkAddress(CreditCard creditCard, Errors errors) {
    Address address = TruConnectUtil.getAddress(creditCard);
    super.validate(address, errors);
  }

  protected boolean mod10(String cardNumber) {
    int[] ar = new int[cardNumber.length()];
    String ccChar;
    int sum = 0;
    for (int i = 0; i < cardNumber.length(); ++i) {
      ccChar = String.valueOf(cardNumber.charAt(i));
      ar[i] = Integer.parseInt(ccChar);
    }
    for (int i = ar.length - 2; i >= 0; i -= 2) {
      ar[i] *= 2;
      if (ar[i] > 9)
        ar[i] -= 9;
    }
    for (int i = 0; i < ar.length; ++i) {
      sum += ar[i];
    }
    return sum % 10 == 0 ? true : false;
  }

}
