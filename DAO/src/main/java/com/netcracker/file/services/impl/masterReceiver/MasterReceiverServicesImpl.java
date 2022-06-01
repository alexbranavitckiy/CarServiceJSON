package com.netcracker.file.services.impl.masterReceiver;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.FileService;
import com.netcracker.LoginServices;
import com.netcracker.MasterReceiverServices;
import com.netcracker.session.UserSession;
import com.netcracker.file.services.impl.CRUDServicesImpl;
import com.netcracker.file.services.impl.login.LoginServicesImpl;
import com.netcracker.user.MasterReceiver;

import java.io.IOException;
import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.UUID;

@Slf4j
public class MasterReceiverServicesImpl implements MasterReceiverServices {

 private FileService fileService = new FileService();
 private LoginServices loginService = new LoginServicesImpl();
 private CRUDServices<MasterReceiver, UUID> searchServices = new CRUDServicesImpl<>();

 @Override
 public List<MasterReceiver> getAllMasterReceiver() throws EmptySearchException {
  return searchServices.getAll(new File(FileService.USER_PATH), MasterReceiver[].class);
 }

 @Override
 @SneakyThrows
 public boolean updateMaster(MasterReceiver masterReceiver) {
  if (this.passwordCheck(masterReceiver) && searchServices.updateObject(
   masterReceiver,
   fileService.getReceiverFile(), MasterReceiver[].class)) {
   return UserSession.updateSession(masterReceiver);
  }
  return false;
 }

 @Override
 @SneakyThrows
 public boolean updateMasterAndSession(MasterReceiver masterReceiver) {
  if (this.passwordCheck(masterReceiver) && searchServices.updateObject(
   masterReceiver,
   fileService.getReceiverFile(), MasterReceiver[].class)) {
   return UserSession.updateSession(masterReceiver);
  }
  return false;
 }

 @Override
 public boolean addMaster(MasterReceiver masterReceiver) {
  if (this.passwordCheck(masterReceiver)) {
   return searchServices.addObject(masterReceiver,
    new File(FileService.RECEIVER_PATH), MasterReceiver[].class);
  }
  return false;
 }

 private boolean passwordCheck(MasterReceiver masterReceiver) {
  try {
   if (loginService.searchByUserLoginAndPassword(masterReceiver.getLogin(),
    masterReceiver.getPassword())) {
    log.info("The username you entered is already taken");
    return false;
   }
  } catch (IOException e) {
   log.error(e.getMessage());
  }
  return true;
 }

}
