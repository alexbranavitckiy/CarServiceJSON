package com.netcracker.jdbc.services.impl.client;

import com.netcracker.ClientServices;
import com.netcracker.LoginServices;
import com.netcracker.errors.PersistException;
import com.netcracker.file.services.impl.login.LoginServicesImpl;
import com.netcracker.jdbc.services.ClientDao;
import com.netcracker.jdbc.services.impl.ClientDaoImpl;
import com.netcracker.user.Client;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientDaoServicesImpl implements ClientServices {

 private final LoginServices loginService = new LoginServicesImpl();
 private final ClientDao clientDao = new ClientDaoImpl();

 @Override
 public List<Client> getAllClient() {
  try {
   return clientDao.getAll();
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return new ArrayList<>();
 }

 @Override
 public boolean addObjectInClient(Client client) {
  try {
   return clientDao.addObject(client);
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return false;
 }

 @Override
 public boolean addObjectInClientNotOnline(Client client) {
  try {
   return clientDao.addObject(client);
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return false;
 }

 @Override
 @SneakyThrows
 public boolean updateClient(Client client) {
  try {
   return clientDao.update(client);
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return false;
 }

 @Override
 @SneakyThrows
 public boolean updateClientNotSession(Client client) {
  try {
   return clientDao.update(client);
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return false;
 }

 private boolean passwordCheck(Client client) {
  try {
   if (loginService.searchByUserLoginAndPassword(client.getLogin(),
    client.getPassword())) {
    log.info("The username you entered is already taken");
    return false;
   }
  } catch (IOException e) {
   log.error(e.getMessage());
  }
  return true;
 }
}