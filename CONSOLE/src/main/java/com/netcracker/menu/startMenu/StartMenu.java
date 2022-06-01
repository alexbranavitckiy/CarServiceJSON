package com.netcracker.menu.startMenu;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.login.LoginMenu;
import com.netcracker.menu.Menu;
import com.netcracker.menu.userMenu.InfoMenu;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;


@Slf4j
public class StartMenu implements Menu {

 private final ServicesFactory servicesFactory;

 public StartMenu(ServicesFactory servicesFactory) {
  this.servicesFactory = servicesFactory;
 }

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1,to exit the application.");
  log.info("Enter 2, to login as a registered user.");
  log.info("Enter 3, for information.");
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  this.preMessage(parentsName);
  String NAME_MENU = "Back to main menu";
  label:
  while (true) {
   switch (in.next()) {
    case "3": {
     new InfoMenu().run(in, NAME_MENU);
     this.preMessage(parentsName);
     break;
    }
    case "2": {
     new LoginMenu(servicesFactory).run(in, NAME_MENU);
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
