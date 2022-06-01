package com.netcracker.menu.order.client;

import com.netcracker.CarServices;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.marka.CarClient;
import com.netcracker.menu.Menu;
import com.netcracker.menu.validator.ValidatorInstruments;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.order.Order;
import com.netcracker.order.State;
import com.netcracker.OrderServices;
import com.netcracker.session.UserSession;

import java.io.IOException;
import java.util.*;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class SignMenu implements Menu {

 private final ServicesFactory servicesFactory;
 private final CarServices carServices;

 public SignMenu(ServicesFactory servicesFactory) {
  this.carServices = servicesFactory.getFactory().getCarServices();
  this.servicesFactory = servicesFactory;
 }

 @Override
 public void preMessage(String nameMenu) {
  log.info("Enter 1. {}", nameMenu);
  log.info("Enter 2. Submit a repair request");
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  OrderServices orderServices = servicesFactory.getFactory().getOrderServices();
  this.preMessage(parentsName);
  label:
  while (true) {
   switch (in.next()) {
    case "1": {
     break label;
    }
    case "2": {
     UserSession.getClientSession().ifPresent(x -> {
      try {
       List<CarClient> carClients = carServices.getCarByIdClient(x.getId());
       if (x.getCarClients().size() > 0) {
        for (int z = 0; z < carClients.size(); z++) {
         log.info("Id:{} Car:{}", z + 1, carClients.get(z));
        }
        try {
         log.info("Enter car id");
         CarClient carClient = carClients.get(in.nextInt() - 1);
         if (orderServices.getOrderByIdCar(carClient.getId()).isEmpty()) {
          UUID orderUUID = UUID.randomUUID();
          log.info("Fill in the request information");
          Order order = Order.builder()
           .id(orderUUID)
           .clientUUID(UserSession.getClientSession().get().getId())
           .stateOrder(State.REQUEST.getId())
           .outfits(new ArrayList<>())
           .createdDate(new Date())
           .idCar(carClient.getId())
           .updatedDate(new Date())
           .label(new ArrayList<>())
           .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
           .build();
          order.setMasterReceiver(servicesFactory.getFactory().getMasterReceiverServices().getAllMasterReceiver().stream().findFirst().get().getId());
          VALIDATOR_INSTRUMENTS.successfullyMessages(orderServices.repairRequest(order));
         } else {
          log.info("Request for an order with this vehicle has been sent");
         }
         this.preMessage(parentsName);
        } catch (IndexOutOfBoundsException e) {
         log.info("The selected car was not found. Please try again");
        }
       } else {
        log.info("At the moment you have no registered cars please log out or add a car");
        this.preMessage(parentsName);
       }
      } catch (EmptySearchException e) {
       e.printStackTrace();
      }
     });
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
