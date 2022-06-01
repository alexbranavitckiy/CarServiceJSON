package com.netcracker.menu.edit;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.menu.validator.ValidatorInstruments;
import com.netcracker.MasterReceiverServices;
import com.netcracker.session.UserSession;
import com.netcracker.user.MasterReceiver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class EditMasterReceiver implements Menu {

 private final MasterReceiver masterReceiver;
 private final ServicesFactory servicesFactory;

 @SneakyThrows
 public EditMasterReceiver(ServicesFactory servicesFactory) {
  this.masterReceiver = UserSession.getCloneMasterReceiverSession();
  this.servicesFactory = servicesFactory;
 }

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1 {}", parentsName);
 }


 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  MasterReceiverServices masterReceiverServices = servicesFactory.getFactory().getMasterReceiverServices();
  log.info("Name");
  if (VALIDATOR_INSTRUMENTS.edit(this.masterReceiver.getName(), in)) {
   this.masterReceiver.setName(VALIDATOR_INSTRUMENTS.validateNameUser(in));
  }
  log.info("Descriptions");
  if (VALIDATOR_INSTRUMENTS.edit(this.masterReceiver.getDescription(), in)) {
   this.masterReceiver.setDescription(VALIDATOR_INSTRUMENTS.validateDescription(in));
  }
  log.info("Login");
  if (VALIDATOR_INSTRUMENTS.edit(this.masterReceiver.getLogin(), in)) {
   this.masterReceiver.setLogin(VALIDATOR_INSTRUMENTS.validateLogin(in));
  }
  log.info("Enter a new password");
  this.masterReceiver.setPassword(VALIDATOR_INSTRUMENTS.validatePassword(in));
  log.info("Education");
  if (VALIDATOR_INSTRUMENTS.edit(this.masterReceiver.getEducation(), in)) {
   this.masterReceiver.setPassword(VALIDATOR_INSTRUMENTS.validateEducation(in));
  }
  log.info("HomeAddress");
  if (VALIDATOR_INSTRUMENTS.edit(this.masterReceiver.getHomeAddress(), in)) {
   this.masterReceiver.setHomeAddress(VALIDATOR_INSTRUMENTS.validateHomeAddress(in));
  }
  log.info("Mail");
  if (VALIDATOR_INSTRUMENTS.edit(this.masterReceiver.getMail(), in)) {
   this.masterReceiver.setMail(masterReceiver.getMail());
  }
  log.info("Phone");
  if (VALIDATOR_INSTRUMENTS.edit(this.masterReceiver.getPhone(), in)) {
   this.masterReceiver.setPhone(VALIDATOR_INSTRUMENTS.validatePhone(in));
  }
  if (masterReceiverServices.updateMaster(masterReceiver)) {
   log.info("Data entered successfully");
  } else {
   log.info("An input error occurred while entering data. Retry data change");
  }
 }
}
