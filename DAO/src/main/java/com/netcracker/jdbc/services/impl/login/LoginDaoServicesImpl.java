package com.netcracker.jdbc.services.impl.login;

import com.netcracker.*;
import com.netcracker.jdbc.services.ClientDao;
import com.netcracker.jdbc.services.CrudDao;
import com.netcracker.jdbc.services.MasterDao;
import com.netcracker.jdbc.services.MasterReceiverDao;
import com.netcracker.jdbc.services.impl.ClientDaoImpl;
import com.netcracker.jdbc.services.impl.MasterDaoImpl;
import com.netcracker.jdbc.services.impl.MasterReceiverDaoImpl;
import com.netcracker.jdbc.services.impl.car.CarDaoServicesImpl;
import com.netcracker.marka.CarClient;
import com.netcracker.session.UserSession;
import com.netcracker.user.Client;
import com.netcracker.user.Master;
import com.netcracker.user.MasterReceiver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class LoginDaoServicesImpl implements LoginServices {

 private final ClientDao clientDao = new ClientDaoImpl();
 private final MasterDao masterDao = new MasterDaoImpl();
 private final MasterReceiverDao masterReceiverDao = new MasterReceiverDaoImpl();
 private final CarServices carServices = new CarDaoServicesImpl();


 public LoginDaoServicesImpl() {
 }

 @Override
 public boolean searchByUserLoginAndPassword(String login, String password) {
  try {
   {
    Optional<Client> client = clientDao.getAllByAnyQuery(clientDao.getSelectByPasswordAndLogin(), password, login).stream().filter(x ->
     {
      if (x != null) {
       return x.getLogin().equalsIgnoreCase(login) && x.getPassword()
        .equalsIgnoreCase(password);
      }
      return false;
     }
    ).findFirst();
    if (client.isPresent()) {
     List<CarClient> carClients = carServices.getCarByIdClient(client.get().getId());
     if (!carClients.isEmpty()) {
      carClients.forEach(x -> client.get().getCarClients().add(x.getId()));
     }
     client.ifPresent(UserSession::openSession);
     return true;
    }
   }
   {
    Optional<Master> master = masterDao.getAllByAnyQuery(masterDao.getSelectByPasswordAndLogin(), password, login).stream().filter(x ->
     {
      if (x != null && x.getLogin() != null && x.getPassword() != null) {
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
   {
    Optional<MasterReceiver> masterReceiver = masterReceiverDao.getAllByAnyQuery(masterReceiverDao.getSelectByPasswordAndLogin(), password, login).stream().
     filter(x ->
      {
       if (x != null && x.getPassword() != null && x.getLogin() != null) {
        return x.getLogin().equalsIgnoreCase(login) && x.getPassword()
         .equalsIgnoreCase(password);
       } else {
        return false;
       }
      }
     ).findFirst();
    if (masterReceiver.isPresent()) {
     masterReceiver.ifPresent(UserSession::openSession);
     return true;
    }
   }
  } catch (Exception e) {
   log.warn("User search failed", e);
  }
  return false;
 }

}