package com.netcracker;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.jdbc.ConnectorDB;
import com.netcracker.menu.startMenu.StartMenu;
import com.netcracker.file.FileService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;


@Slf4j
public class Main {
 public static void main(String[] arg) throws IOException {
  ResourceBundle resource = ResourceBundle.getBundle("persistent");//factory will be selected depending on the value
  try (Scanner in = new Scanner(System.in)) {
   new FileService().initMethod();//initialization data input method run with empty files
   new StartMenu(new ServicesFactory(resource.getString("persistent"))).run(in, "");
  } finally {
   ConnectorDB.getDs().close();
  }
 }
}

