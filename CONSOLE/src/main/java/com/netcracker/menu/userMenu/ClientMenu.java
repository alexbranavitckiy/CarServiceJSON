package com.netcracker.menu.userMenu;

import com.netcracker.CarServices;
import com.netcracker.OrderServices;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.marka.CarClient;
import com.netcracker.menu.Menu;
import com.netcracker.menu.car.CarMenu;
import com.netcracker.menu.edit.EditClient;
import com.netcracker.menu.order.client.SignMenu;
import com.netcracker.ClientServices;
import com.netcracker.order.State;
import com.netcracker.session.UserSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ClientMenu implements Menu {

 private final ClientServices clientServices;
 private final ServicesFactory servicesFactory;
 private final CarServices carServices;
 private final OrderServices orderServices;

 public ClientMenu(ServicesFactory servicesFactory) {
  this.servicesFactory = servicesFactory;
  this.orderServices = servicesFactory.getFactory().getOrderServices();
  this.carServices = servicesFactory.getFactory().getCarServices();
  this.clientServices = servicesFactory.getFactory().getClientServices();
 }

 @Override
 public void preMessage(String nameMenu) {
  log.info("Enter 1 {}", nameMenu);
  log.info("Enter 2 for contact information");
  log.info("Enter 3 to open recording menu:\n-Sign up for repairs.\n-View car status.");
  log.info(
   "Enter 4 to get information about the car:\n-View the list of cars.\n-Edit information on car");
  log.info("Enter 5 to edit information about the client");
  log.info("Enter 6 Cancel order");
  log.info("Number of cars in work:{}", carServices.getAllCarClientWaitState(State.IN_WORK, UserSession.getClientSession().get().getId()).size());
  log.info("Repair requests:{}", carServices.getAllCarClientWaitState(State.REQUEST, UserSession.getClientSession().get().getId()).size());
  log.info("Ready orders:{}", carServices.getAllCarClientWaitState(State.WAIT_CLIENT, UserSession.getClientSession().get().getId()).size());
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  log.info("You have entered the client menu");
  this.preMessage(parentsName);
  label:
  while (true) {
   switch (in.next()) {
    case "1": {
     break label;
    }
    case "2": {
     new InfoMenu().run(in, "Main menu");
     this.preMessage(parentsName);
     break;
    }
    case "3": {
     new SignMenu(servicesFactory).run(in, "Main menu");
     this.preMessage(parentsName);
     break;
    }
    case "4": {
     new CarMenu(servicesFactory).run(in, "Main menu");
     this.preMessage(parentsName);
     break;
    }
    case "5": {
     EditClient editClient = new EditClient();
     editClient.run(in, "Main menu");
     if (clientServices.updateClient(editClient.getClient())) {
      log.info("Data entered successfully");
     } else {
      log.info("An input error occurred while entering data. Retry data change");
     }
     this.preMessage(parentsName);
     break;
    }
    case "6": {
     List<CarClient> carClients = carServices.getAllCarClientWaitState(State.REQUEST, UserSession.getClientSession().get().getId());
     for (int x = 0; x < carClients.size(); x++) {
      log.info(" Repair requests:[{}]{}", x + 1, carClients.get(x));
     }
     log.info("Select and cancel request?\n 1-yeas.\n 2-no");
     if (in.next().equalsIgnoreCase("1"))
      log.info("Enter id.");
     try {
      int index = in.nextInt();
      orderServices.cancelRequest(carClients.get(index - 1).getId());
      log.info("Request canceled:{}", carClients.get(index - 1));
     } catch (IndexOutOfBoundsException e) {
      log.info("Invalid index entered");
     }
     this.preMessage(parentsName);
     break;
    }
    default: {
     this.preMessage(parentsName);
     break;
    }
   }
  }
 }
}
