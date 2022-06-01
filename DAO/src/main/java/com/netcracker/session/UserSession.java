package com.netcracker.session;

import com.netcracker.user.Client;
import com.netcracker.user.Master;
import com.netcracker.user.MasterReceiver;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class UserSession {

 private static MasterReceiver masterReceiverSession;
 private static Master masterSession;
 private static Client clientSession;

 private UserSession() {
 }

 public static void openSession(Object object) {
  if (clientSession == null && object instanceof Client) {
   clientSession = (Client) object;
   closeSession(masterSession, masterReceiverSession);
   return;
  }
  if (masterSession == null && object instanceof Master) {
   masterSession = (Master) object;
   closeSession(clientSession, masterReceiverSession);
   return;
  }
  if (masterReceiverSession == null && object instanceof MasterReceiver) {
   masterReceiverSession = (MasterReceiver) object;
   closeSession(clientSession, masterSession);
  }
 }

 public static boolean updateSession(Object object) throws CloneNotSupportedException {
  if (object instanceof Client) {
   clientSession = UserSession.getCloneClient((Client) object);
   closeSession(masterSession, masterReceiverSession);
   return true;
  }
  if (object instanceof Master) {
   masterSession = (Master) object;
   closeSession(clientSession, masterReceiverSession);
   return true;
  }
  if (object instanceof MasterReceiver) {
   masterReceiverSession = (MasterReceiver) object;
   closeSession(clientSession, masterSession);
   return true;
  }
  return false;
 }

 public static void closeSession(Object... o) {
  Arrays.stream(o).forEach(x -> {
   if (x instanceof Client) {
    clientSession = (Client) x;
    masterSession = null;
    masterReceiverSession = null;
   }
   if (x instanceof Master) {
    masterSession = (Master) x;
    clientSession = null;
    masterReceiverSession = null;
   }
   if (x instanceof MasterReceiver) {
    masterReceiverSession = (MasterReceiver) x;
    clientSession = null;
    masterSession = null;
   }
  });
 }

 public static void closeSession() {
  masterSession = null;
  masterReceiverSession = null;
  clientSession = null;
 }

 public static Optional<MasterReceiver> getMasterReceiverSession() {//return immutable object
  return Optional.ofNullable(masterReceiverSession);
 }

 public static Optional<Master> getMasterSession() {//return immutable object
  return Optional.ofNullable(masterSession);
 }

 public static MasterReceiver getCloneMasterReceiverSession() throws CloneNotSupportedException {
  return masterReceiverSession.clone();
 }

 public static Client getCloneClient(Client client) throws CloneNotSupportedException {
  return client.clone();
 }

 public static Client getCloneClientSession() throws CloneNotSupportedException {
  return clientSession.clone();
 }

 public static Optional<Client> getClientSession() {
  try {
   if (clientSession != null) {
    return Optional.ofNullable(getCloneClient(clientSession));
   }
  } catch (CloneNotSupportedException clientSession) {
   log.warn(clientSession.getMessage());
  }
  return Optional.empty();
 }

}



