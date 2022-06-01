package com.netcracker.jdbc.services.impl.masterReceiver;

import com.netcracker.LoginServices;
import com.netcracker.MasterReceiverServices;
import com.netcracker.jdbc.services.CrudDao;
import com.netcracker.jdbc.services.impl.MasterReceiverDaoImpl;
import com.netcracker.jdbc.services.impl.login.LoginDaoServicesImpl;
import com.netcracker.session.UserSession;
import com.netcracker.user.MasterReceiver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
public class MasterReceiverDaoServicesImpl implements MasterReceiverServices {

 private LoginServices loginService = new LoginDaoServicesImpl();
 private CrudDao<MasterReceiver, UUID> crudServices = new MasterReceiverDaoImpl();

 @Override
 @SneakyThrows
 public List<MasterReceiver> getAllMasterReceiver() {
  return crudServices.getAll();
 }

 @Override
 @SneakyThrows
 public boolean updateMaster(MasterReceiver masterReceiver) {
  if (crudServices.update(
   masterReceiver)) {
   return UserSession.updateSession(masterReceiver);
  }
  return false;
 }

 @Override
 @SneakyThrows
 public boolean updateMasterAndSession(MasterReceiver masterReceiver) {
  if (this.passwordCheck(masterReceiver) && crudServices.update(
   masterReceiver)) {
   return UserSession.updateSession(masterReceiver);
  }
  return false;
 }

 @Override
 @SneakyThrows
 public boolean addMaster(MasterReceiver masterReceiver) {
  if (this.passwordCheck(masterReceiver)) {
   return crudServices.addObject(masterReceiver);
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
