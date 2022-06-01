package com.netcracker.menu.registration;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.MasterReceiverServices;
import com.netcracker.MasterServices;
import com.netcracker.user.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class RegistrationMaster implements Menu {

 private final MasterServices masterServices;
 private final MasterReceiverServices masterReceiverServices;

 public RegistrationMaster(ServicesFactory servicesFactory) {
  masterServices = servicesFactory.getFactory().getMasterServices();
  masterReceiverServices = servicesFactory.getFactory().getMasterReceiverServices();
 }

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1 {}", parentsName);
  log.info("Enter 2 to continue create");
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  this.preMessage(parentsName);
  label:
  while (true) {
   switch (in.next()) {
    case "2": {
     info();
     String str = in.next();
     if (str.equalsIgnoreCase("1")) {
      Master master = Master.builder()
       .id(UUID.randomUUID())
       .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
       .homeAddress(VALIDATOR_INSTRUMENTS.validateHomeAddress(in))
       .education(VALIDATOR_INSTRUMENTS.validateEducation(in))
       .name(VALIDATOR_INSTRUMENTS.validateNameUser(in))
       .password(VALIDATOR_INSTRUMENTS.validatePassword(in))
       .mail(VALIDATOR_INSTRUMENTS.validateMail(in))
       .phone(VALIDATOR_INSTRUMENTS.validatePhone(in))
       .login(VALIDATOR_INSTRUMENTS.validateLogin(in))
       .qualificationEnum(VALIDATOR_INSTRUMENTS.qualificationEnum(in))
       .outfits(new ArrayList<>())
       .role(Role.MASTER)
       .build();
      if (this.masterServices.addMaster(master)) {
       log.info("Data saved successfully");
      } else {
       log.info("Data not saved please try again");
      }
      break label;
     }
     if (str.equalsIgnoreCase("2")) {
      MasterReceiver masterReceiver = MasterReceiver.builder()
       .id(UUID.randomUUID())
       .education(VALIDATOR_INSTRUMENTS.validateEducation(in))
       .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
       .homeAddress(VALIDATOR_INSTRUMENTS.validateHomeAddress(in))
       .qualificationEnum(VALIDATOR_INSTRUMENTS.qualificationEnum(in))
       .mail(VALIDATOR_INSTRUMENTS.validateMail(in))
       .login(VALIDATOR_INSTRUMENTS.validateLogin(in))
       .password(VALIDATOR_INSTRUMENTS.validatePassword(in))
       .role(Role.RECEPTIONIST)
       .name(VALIDATOR_INSTRUMENTS.validateNameUser(in))
       .orders(new ArrayList<>())
       .build();
      if (this.masterReceiverServices.addMaster(masterReceiver)) {
       log.info("Data saved successfully");
      } else {
       log.info("Data not saved please try again");
      }
      break label;
     } else {
      this.preMessage(parentsName);
      continue label;
     }
    }
    case "1": {
     break label;
    }

    case "3": {
     this.preMessage(parentsName);
     break label;
    }
    default: {
     this.preMessage(parentsName);
     break;
    }
   }
  }
 }

 private void info() {
  log.info("Enter 1 create MASTER");
  log.info("Enter 2 create RECEPTIONIST");
 }

}