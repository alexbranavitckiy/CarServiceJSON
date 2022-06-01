package com.netcracker.jdbc.services.impl.master;

import com.netcracker.LoginServices;
import com.netcracker.MasterServices;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.errors.PersistException;
import com.netcracker.file.services.impl.login.LoginServicesImpl;
import com.netcracker.jdbc.services.CrudDao;
import com.netcracker.jdbc.services.impl.MasterDaoImpl;
import com.netcracker.user.Master;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class MasterDaoServicesImpl implements MasterServices {

 private final LoginServices loginService = new LoginServicesImpl();
 private final CrudDao<Master, UUID> crudServices = new MasterDaoImpl();

 public MasterDaoServicesImpl() {
 }

 @Override
 public boolean addMaster(Master master) {
  try {
   if (this.passwordCheck(master)) {
    return this.crudServices.addObject(master);
   }
  } catch (Exception e) {
   log.error("Error adding object:{}", e.getMessage());
  }
  return false;
 }

 @Override
 public Optional<Master> getMasterById(UUID master) {
  try {
   return crudServices.getById(master);
  } catch (PersistException p) {
   log.warn("MasterById error:{}", p.getMessage());
  }
  return Optional.empty();
 }

 @Override
 public List<Master> getAllMaster() {
  try {
   return this.crudServices.getAll();
  } catch (PersistException p) {
   log.warn("AllMaster error:{}", p.getMessage());
  }
  return new ArrayList<>();
 }

 public boolean passwordCheck(Master master) {
  try {
   if (loginService.searchByUserLoginAndPassword(master.getLogin(),
    master.getPassword())) {
    log.info("The username you entered is already taken");
    return false;
   }
  } catch (IOException e) {
   log.warn(e.getMessage());
  }
  return true;
 }

 @Override
 public boolean updateMaster(Master master) {
  try {
   return crudServices.update(master);
  } catch (PersistException p) {
   log.warn("Update error:{}", p.getMessage());
  }
  return false;
 }
}
