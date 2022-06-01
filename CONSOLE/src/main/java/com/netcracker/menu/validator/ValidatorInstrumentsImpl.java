package com.netcracker.menu.validator;

import com.netcracker.menu.errors.InvalidValuesException;
import com.netcracker.order.State;
import com.netcracker.user.Qualification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public enum ValidatorInstrumentsImpl implements ValidatorInstruments {

 VALIDATOR_INSTRUMENTS;

 private final StringBuilder stringBuilder = new StringBuilder(20);

 private final String EMAIL_PATTERN =
  "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
   + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

 private final String YEAR_PATTERN =
  "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

 private final String TIME_PATTERN =
  "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

 private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

 @Override
 public boolean inEmail(String email) throws InvalidValuesException {
  if (email != null && !email.isBlank()) {
   Matcher matcher = pattern.matcher(email);
   return matcher.matches();
  } else {
   throw new InvalidValuesException(
    "Invalid values, please try again. Format: Leha03377@Hotmail.com");
  }
 }

 @Override
 @SneakyThrows
 public Date getDate(Scanner in) {
  String pattern = "yyyy-MM-dd HH:mm:ssZ";
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
  return simpleDateFormat.parse(this.validateYear(in) + " " + this.validatorTime(in) + ":00+0300");
 }

 @Override
 public String validatorTime(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter time. HH:MM");
  stringBuilder.append(in.next());
  if (Pattern.compile(TIME_PATTERN).matcher(stringBuilder.toString().toLowerCase()).find()) {
   return stringBuilder.toString();
  }
  return validatorTime(in);
 }

 @Override
 public String validateYear(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter year. YYY-MM-DD");
  stringBuilder.append(in.next());
  if (Pattern.compile(YEAR_PATTERN).matcher(stringBuilder.toString().toLowerCase()).find()) {
   return stringBuilder.toString();
  }
  log.info("Year must contain from 4 numbers and made up of numbers");
  return validateYear(in);
 }


 @Override
 public UUID stateOutfit(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Outfit status");
  log.info("Enter 1 create: END");
  log.info("Enter 2 create: WORK");
  log.info("Enter 3 create: RECORDED");
  log.info("Enter 4 create: NO_STATE");
  stringBuilder.append(in.next());
  switch (stringBuilder.toString()) {
   case "1": {
    return com.netcracker.outfit.State.END.getId();
   }
   case "2": {
    return com.netcracker.outfit.State.WORK.getId();
   }
   case "3": {
    return com.netcracker.outfit.State.RECORDED.getId();
   }
   case "4": {
    return com.netcracker.outfit.State.NO_STATE.getId();
   }
   default: {
    return stateOutfit(in);
   }
  }
 }

 @Override
 public UUID orderStateCarBr(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter 1 : CORRECTED");
  log.info("Enter 2 : NOT_FIXED");
  log.info("Enter 3 : IMPORTANT");
  log.info("Enter 4 : NEEDS_CORRECTED");
  stringBuilder.append(in.next());
  switch (stringBuilder.toString()) {
   case "1": {
    return com.netcracker.breakdown.State.CORRECTED.getId();
   }
   case "2": {
    return com.netcracker.breakdown.State.NOT_FIXED.getId();
   }
   case "3": {
    return com.netcracker.breakdown.State.IMPORTANT.getId();
   }
   case "4": {
    return com.netcracker.breakdown.State.NEEDS_CORRECTED.getId();
   }
   default: {
    return orderStateCarBr(in);
   }
  }
 }


 @Override
 public UUID orderState(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter 1 create: TEMPLATE");
  log.info("Enter 2 create: RECORDED");
  log.info("Enter 3 create: IN_WORK");
  log.info("Enter 4 create: CAR_GIVEN");
  log.info("Enter 5 create: CAR_ACCEPTED");
  log.info("Enter 6 create: WAIT_CLIENT");
  log.info("Enter 7 create: BID");
  stringBuilder.append(in.next());
  switch (stringBuilder.toString()) {
   case "1": {
    return State.TEMPLATE.getId();
   }
   case "2": {
    return State.RECORDED.getId();
   }
   case "3": {
    return State.IN_WORK.getId();
   }
   case "4": {
    return State.CAR_GIVEN.getId();
   }
   case "5": {
    return State.CAR_ACCEPTED.getId();
   }
   case "6": {
    return State.WAIT_CLIENT.getId();
   }
   case "7": {
    return State.BID.getId();
   }
   default: {
    return orderState(in);
   }
  }
 }

 @Override
 public void successfullyMessages(boolean flag) {//recursion!
  if (flag) {
   log.info("Data saved successfully");
  } else {
   log.info("Data not saved please try again");
  }
 }


 @Override
 public Qualification qualificationEnum(Scanner in) {//recursion!
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter 1 create: DISC_EDITING");
  log.info("Enter 2 create: ELECTRICIAN");
  stringBuilder.append(in.next());
  if (stringBuilder.toString().equalsIgnoreCase("1")) {
   return Qualification.DISC_EDITING;
  }
  if (stringBuilder.toString().equalsIgnoreCase("2")) {
   return Qualification.ELECTRICIAN;
  }
  return qualificationEnum(in);
 }

 @Override
 public String validateLogin(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter login");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 3 && stringBuilder.length() < 20) {
   return stringBuilder.toString();
  }
  log.info("Login must contain from 3 to 20 characters");
  return validateLogin(in);
 }

 @Override
 public String validatePhone(Scanner in) {
  log.info("Enter phone");
  stringBuilder.delete(0, stringBuilder.length());
  stringBuilder.append(in.next());
  if (stringBuilder.length() == 13 && stringBuilder.charAt(0) == '+'
   && stringBuilder.charAt(1) == '3') {
   return stringBuilder.toString();
  }
  log.info("Enter must contain +375 ## #######");
  return validatePhone(in);
 }


 @Override
 public String validateMileage(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter Mileage");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 0 && stringBuilder.length() < 20) {
   return stringBuilder.toString();
  }
  log.info("Mileage must contain from 0 to 20 numbers: Enter in the format 300400");
  return validateMileage(in);
 }

 @Override
 public double validatePrice(Scanner in) {
  log.info("Enter Price");
  return in.nextDouble();
 }


 @Override
 public String validateNumberCar(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter number of car");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 5 && stringBuilder.length() < 20) {
   return stringBuilder.toString();
  }
  log.info("Vehicle number must contain at least 7 characters");
  return validateNumberCar(in);
 }

 @Override
 public String validateNameOutfit(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter Name outfit");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 1 && stringBuilder.length() < 20) {
   return stringBuilder.toString();
  }
  log.info("Name must contain from 1 to 20 characters");
  return validateNameOutfit(in);
 }


 @Override
 public String validateNameUser(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter Name");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 3 && stringBuilder.length() < 20) {
   return stringBuilder.toString();
  }
  log.info("Name must contain from 3 to 20 characters");
  return validateNameUser(in);
 }

 @Override
 public boolean edit(String fieldName, Scanner in) {
  log.info(fieldName);
  log.info("Enter 1 to skip");
  log.info("Enter 2 to edit");
  return in.next().equalsIgnoreCase("2");
 }


 @Override
 public String validateSummary(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter Summary, describe the car damage");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 3 && stringBuilder.length() < 20) {
   return stringBuilder.toString();
  }
  log.info("Description must contain from 4 to 50 characters");
  return validateDescription(in);
 }

 @Override
 public String validateDescription(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter Description");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 3 && stringBuilder.length() < 50) {
   return stringBuilder.toString();
  }
  log.info("Description must contain from 4 to 50 characters");
  return validateDescription(in);
 }

 @Override
 public String validateEducation(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter Education");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 3 && stringBuilder.length() < 50) {
   return stringBuilder.toString();
  }
  log.info("Education must contain from 4 to 50 characters");
  return validateEducation(in);
 }

 @Override
 public String validateMail(Scanner in) {//recursion!
  try {
   stringBuilder.delete(0, stringBuilder.length());
   log.info("Enter Mail");
   stringBuilder.append(in.next());
   if (this.inEmail(stringBuilder.toString())) {
    return stringBuilder.toString();
   }
   log.info("Invalid values, please try again. Format: Leha03377@Hotmail.com");
  } catch (InvalidValuesException e) {
   log.warn("Invalid values entered:{}", e.getLocalizedMessage());
  }
  return this.validateMail(in);
 }

 @Override
 public String validateHomeAddress(Scanner in) {
  log.info("Enter HomeAddress");
  return in.next();
 }


 @Override
 public String validatePassword(Scanner in) {
  stringBuilder.delete(0, stringBuilder.length());
  log.info("Enter password");
  stringBuilder.append(in.next());
  if (stringBuilder.length() > 3 && stringBuilder.length() < 20) {
   return stringBuilder.toString();
  }
  log.info("Password must contain from 3 to 20 characters");
  return validatePassword(in);

 }
}
