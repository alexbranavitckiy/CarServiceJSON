package com.netcracker.menu.login;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.menu.registration.RegistrationClient;
import com.netcracker.session.UserSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class LoginMenu implements Menu {


 private final ServicesFactory servicesFactory;

 public LoginMenu(ServicesFactory servicesFactory) {
  this.servicesFactory = servicesFactory;
 }

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1:{}", parentsName);
  log.info("Enter 2 to login.");
  log.info("Enter 3 to registration.");
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  this.preMessage(parentsName);
  label:
  while (true) {
   switch (in.next()) {
    case "2": {
     UserSession.closeSession();
     log.info("Enter login.");
     String login = in.next();
     log.info("Enter password");
     String password = in.next();
     if (servicesFactory.getFactory().getLoginServices().searchByUserLoginAndPassword(login, password)) {
      new EnterLogin(servicesFactory).run(in, "Login menu");
     } else {
      log.info("User is not found");
     }
     this.preMessage(parentsName);
     break;
    }
    case "3": {
     new RegistrationClient(servicesFactory).run(in, "Login menu");
     this.preMessage(parentsName);
     break;
    }
    case "1": {
     break label;
    }
    default: {
     this.preMessage(parentsName);
     break;
    }
   }
  }
 }

}
