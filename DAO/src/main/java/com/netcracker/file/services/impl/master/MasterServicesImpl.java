package com.netcracker.file.services.impl.master;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.FileService;
import com.netcracker.LoginServices;
import com.netcracker.MasterServices;
import com.netcracker.file.services.impl.CRUDServicesImpl;
import com.netcracker.file.services.impl.login.LoginServicesImpl;
import com.netcracker.user.Master;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MasterServicesImpl implements MasterServices {

 private final FileService fileService = new FileService();
 private final LoginServices loginService = new LoginServicesImpl();
 private final CRUDServices<Master, UUID> crudServices = new CRUDServicesImpl<>();

 public MasterServicesImpl() {
 }

 public boolean addMaster(Master master) {
  try {
   if (this.passwordCheck(master)) {
    return this.crudServices.addObject(master, fileService.getMasterFile(), Master[].class);
   }
  } catch (Exception e) {
   log.error("Error adding object", e);
  }
  return false;
 }

 @Override
 public Optional<Master> getMasterById(UUID master) throws EmptySearchException {
  return this.getAllMaster().stream().filter(x -> x.getId().equals(master)).findFirst();

 }

 public List<Master> getAllMaster() throws EmptySearchException {
  return this.crudServices.getAll(fileService.getMasterFile(), Master[].class);
 }


 public boolean passwordCheck(Master master) {
  try {
   if (loginService.searchByUserLoginAndPassword(master.getLogin(),
    master.getPassword())) {
    log.info("The username you entered is already taken");
    return false;
   }
  } catch (IOException e) {
   log.error(e.getMessage());
  }
  return true;
 }


 @Override
 public boolean updateMaster(Master master) {
  return this.crudServices.updateObject(master, fileService.getMasterFile(), Master[].class);
 }
}
