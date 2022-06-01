package com.netcracker.menu.edit;

import com.netcracker.menu.Menu;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.menu.validator.ValidatorInstruments;
import com.netcracker.session.UserSession;
import com.netcracker.user.Client;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;


@Slf4j
public class EditClient implements Menu {

 private final Client client;

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1 {}", parentsName);
 }

 @SneakyThrows
 public EditClient() {
  this.client = UserSession.getCloneClientSession();
 }

 public EditClient(Client client) {
  this.client=client;
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  log.info("Descriptions");
  if (VALIDATOR_INSTRUMENTS.edit(this.client.getDescription(), in)) {
   this.client.setDescription(VALIDATOR_INSTRUMENTS.validateDescription(in));
  }
  log.info("Name");
  if (VALIDATOR_INSTRUMENTS.edit(this.client.getName(), in)) {
   this.client.setName(VALIDATOR_INSTRUMENTS.validateNameUser(in));
  }
  log.info("Login");
  if (VALIDATOR_INSTRUMENTS.edit(this.client.getLogin(), in)) {
   this.client.setLogin(VALIDATOR_INSTRUMENTS.validateLogin(in));
  }
  log.info("Password");
  if (VALIDATOR_INSTRUMENTS.edit(this.client.getPassword(), in)) {
   this.client.setPassword(VALIDATOR_INSTRUMENTS.validatePassword(in));
  }
  log.info("Phone");
  if (VALIDATOR_INSTRUMENTS.edit(this.client.getPhone(), in)) {
   this.client.setPhone(VALIDATOR_INSTRUMENTS.validatePhone(in));
  }
  log.info("Email");
  if (VALIDATOR_INSTRUMENTS.edit(this.client.getEmail(), in)) {
   this.client.setPassword(VALIDATOR_INSTRUMENTS.validatePassword(in));
  }
 }

 public Client getClient() {
  return client;
 }

}