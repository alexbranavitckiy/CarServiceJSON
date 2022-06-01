package com.netcracker.menu.login;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.menu.userMenu.ClientMenu;
import com.netcracker.menu.userMenu.MasterMenu;
import com.netcracker.menu.userMenu.MasterReceiverMenu;
import com.netcracker.session.UserSession;

import java.io.IOException;
import java.util.Scanner;

public class EnterLogin implements Menu {

 private final ServicesFactory servicesFactory;

 EnterLogin(ServicesFactory servicesFactory) {
  this.servicesFactory = servicesFactory;
 }

 @Override
 public void preMessage(String parentsName) {
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  this.preMessage(parentsName);
  if (UserSession.getMasterSession().isPresent()) {
   new MasterMenu(servicesFactory).run(in, "Go out");
  }
  if (UserSession.getMasterReceiverSession().isPresent()) {
   new MasterReceiverMenu(servicesFactory).run(in, "Go out");
  }
  if (UserSession.getClientSession().isPresent()) {
   new ClientMenu(servicesFactory).run(in, "Go out");
  }
 }
}
