package com.netcracker.errors;

public class PersistException extends Exception {

  public PersistException(Exception message) {
    super(message);
  }

  public PersistException(String message) {
    super(message);
  }
}


