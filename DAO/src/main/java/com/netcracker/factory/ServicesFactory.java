package com.netcracker.factory;



public class ServicesFactory {

 private final DaoFactory factory;

 public ServicesFactory(String  nameFactory) {
  if (nameFactory.equalsIgnoreCase("file")) {
   this.factory = new FileDaoFactory();
  } else {
   this.factory = new JDBCDaoFactory();
  }
 }

 public DaoFactory getFactory() {
  return factory;
 }

}
