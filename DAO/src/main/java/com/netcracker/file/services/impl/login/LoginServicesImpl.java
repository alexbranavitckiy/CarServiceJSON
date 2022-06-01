package com.netcracker.file.services.impl.login;

import com.netcracker.file.FileService;
import com.netcracker.LoginServices;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.services.impl.CRUDServicesImpl;
import com.netcracker.session.UserSession;
import com.netcracker.user.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class LoginServicesImpl implements LoginServices {

 private final FileService fileService = new FileService();
 private final CRUDServices<Client, UUID> clientCRUDServices = new CRUDServicesImpl<>();
 private final CRUDServices<Master, UUID> masterCRUDServices = new CRUDServicesImpl<>();
 private final CRUDServices<MasterReceiver, UUID> masterReceiverCRUDServices = new CRUDServicesImpl<>();

 public LoginServicesImpl() {
 }

 @Override
 public boolean searchByUserLoginAndPassword(String login, String password) throws IOException {
  try {
   {
    if (fileService.isExistsUser()) {
     Optional<Client> client = clientCRUDServices.getAll(fileService.getUserFile(),
      Client[].class).stream().filter(x ->
      {
       if (x != null) {
        return x.getLogin().equalsIgnoreCase(login) && x.getPassword()
         .equalsIgnoreCase(password);
       }
       return false;
      }
     ).findFirst();
     if (client.isPresent()) {
      client.ifPresent(UserSession::openSession);
      return true;
     }
    }
   }
   {
    if (fileService.isExistsMaster()) {
     Optional<Master> master = masterCRUDServices.getAll(fileService.getMasterFile(),
      Master[].class).stream().filter(x ->
      {
       if (x != null) {
        return x.getLogin().equalsIgnoreCase(login) && x.getPassword()
         .equalsIgnoreCase(password);
       } else {
        return false;
       }
      }
     ).findFirst();
     if (master.isPresent()) {
      master.ifPresent(UserSession::openSession);
      return true;
     }
    }
   }
   {
    if (fileService.isExistsReceiver()) {
     Optional<MasterReceiver> masters = masterReceiverCRUDServices.getAll(
      fileService.getReceiverFile(), MasterReceiver[].class).stream().
      filter(x ->
       {
        if (x != null) {
         return x.getLogin().equalsIgnoreCase(login) && x.getPassword()
          .equalsIgnoreCase(password);
        } else {
         return false;
        }
       }
      ).findFirst();
     if (masters.isPresent()) {
      masters.ifPresent(UserSession::openSession);
      return true;
     }
    }
   }
  } catch (Exception e) {
   new FileService().init();
   this.searchByUserLoginAndPassword(login, password);
  }
  return false;
 }
}
