package com.netcracker.file.services.impl.client;

import com.netcracker.ClientServices;
import com.netcracker.LoginServices;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.FileService;
import com.netcracker.file.services.impl.CRUDServicesImpl;
import com.netcracker.file.services.impl.login.LoginServicesImpl;
import com.netcracker.session.UserSession;
import com.netcracker.user.Client;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ClientServicesImpl implements ClientServices {

 private final FileService fileService = new FileService();
 private final LoginServices loginService = new LoginServicesImpl();
 private final CRUDServices<Client, UUID> crudServices = new CRUDServicesImpl<>();

 @Override
 public List<Client> getAllClient() throws EmptySearchException {
  return crudServices.getAll(new File(FileService.USER_PATH), Client[].class);
 }

 public boolean addObjectInClient(Client o) {
  if (this.passwordCheck(o)) {
   return this.crudServices.addObject(o, fileService.getUserFile(), Client[].class);
  }
  return false;
 }

 @Override
 @SneakyThrows
 public boolean updateClient(Client client) {
  if (crudServices.updateObject(client,
   new File(FileService.USER_PATH), Client[].class)) {
   return UserSession.updateSession(client);
  }
  return false;
 }

 @Override
 public boolean updateClientNotSession(Client client) {
  if (this.passwordCheck(client)) {
   return crudServices.updateObject(client,
    new File(FileService.USER_PATH), Client[].class);
  }
  return false;
 }


 @Override
 public boolean addObjectInClientNotOnline(Client client) {
  if (this.passwordCheck(client)) {
   return this.crudServices.addObject(client, fileService.getUserFile(), Client[].class);
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
