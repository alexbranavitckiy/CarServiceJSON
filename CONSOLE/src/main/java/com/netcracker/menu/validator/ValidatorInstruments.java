package com.netcracker.menu.validator;


import com.netcracker.user.Qualification;

import java.util.Date;
import java.util.Scanner;

import com.netcracker.menu.errors.InvalidValuesException;

import java.util.UUID;

public interface ValidatorInstruments {

 boolean inEmail(String email) throws InvalidValuesException;

 String validateSummary(Scanner in);

 Date getDate(Scanner in);

 String validatorTime(Scanner in);

 Qualification qualificationEnum(Scanner in);

 UUID orderState(Scanner in);

 String validateLogin(Scanner in);

 String validatePhone(Scanner in);

 String validateMileage(Scanner in);

 String validateNameUser(Scanner in);

 String validateDescription(Scanner in);

 String validateEducation(Scanner in);

 String validateMail(Scanner in);

 double validatePrice(Scanner in);

 String validateNameOutfit(Scanner in);

 String validateHomeAddress(Scanner in);

 String validatePassword(Scanner in);

 String validateYear(Scanner in);

 String validateNumberCar(Scanner in);

 boolean edit(String fieldName, Scanner in);

 UUID stateOutfit(Scanner in);

 void successfullyMessages(boolean flag);

 UUID orderStateCarBr(Scanner in);
}
