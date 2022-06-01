package com.netcracker.menu.registration;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.marka.CarClient;
import com.netcracker.menu.Menu;
import com.netcracker.menu.car.CreateCarClient;
import com.netcracker.menu.order.NewOrder;
import com.netcracker.ClientServices;
import com.netcracker.user.Client;
import com.netcracker.user.RoleUser;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class RegistrationClientByMaster implements Menu {

  private final ClientServices clientServices;
  private final ServicesFactory servicesFactory;

  public RegistrationClientByMaster(ServicesFactory servicesFactory) {
    this.servicesFactory = servicesFactory;
    this.clientServices = servicesFactory.getFactory().getClientServices();
  }

  @Override
  public void preMessage(String parentsName) {
    log.info("Enter 1 {}", parentsName);
    log.info("Enter 2 create a client");
  }

  @Override
  public void run(Scanner in, String parentsName) throws IOException {
    Client clientLast = null;
    UUID uuidCNewClient = UUID.randomUUID();
    CreateCarClient carClientMenu = new CreateCarClient();
    CarClient carClient = carClientMenu.createCar(in, "Filling in car details", uuidCNewClient);
    this.preMessage(parentsName);
    label:
    while (true) {
      switch (in.next()) {
        case "2": {
          log.info("Filling in customer data");
          if (carClient != null) {
            Client client = Client.builder()
                .id(uuidCNewClient)
                .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
                .email(VALIDATOR_INSTRUMENTS.validateMail(in))
                .name(VALIDATOR_INSTRUMENTS.validateNameUser(in))
                .roleUser(RoleUser.UNREGISTERED.getId())
                .carClients(new ArrayList<>())
                .login(carClient.getMetadataCar())
                .password(carClient.getMetadataCar())
                .build();
            if (clientServices.addObjectInClientNotOnline(client) &&
                servicesFactory.getFactory().getCarServices().addCar(carClient)) {
              log.info("User created successfully");
              clientLast = client;
              log.info("Enter 3 to Create an order with these customers");
            } else {
              log.info("Invalid data. Repeat registration");
            }
          } else {
            log.info("Try again to enter information");
          }
          this.preMessage(parentsName);
          break;
        }
        case "1": {
          break label;
        }
        case "3": {
          if (clientLast != null) {
            NewOrder newOrder = new NewOrder(servicesFactory);
            newOrder.createOrder(in, clientLast, carClient.getId());
            newOrder.run(in, "Client creation menu");
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
