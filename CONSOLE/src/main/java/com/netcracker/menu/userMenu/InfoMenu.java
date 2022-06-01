package com.netcracker.menu.userMenu;

import com.netcracker.menu.Menu;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class InfoMenu implements Menu {

 @Override
 public void preMessage(String nameMenu) {

 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  log.info(
   "Contact Information:\nAddress: Minsk, Gintovta st., 1, 3rd floor\nPhone:+375(33)333-83-79\nOpening hours:8.00-23.00");
 }
}
