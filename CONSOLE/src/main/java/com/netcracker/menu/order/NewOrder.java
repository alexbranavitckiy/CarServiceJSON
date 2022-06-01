package com.netcracker.menu.order;

import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.menu.car.CreateOutfit;
import com.netcracker.order.Order;
import com.netcracker.OrderServices;

import com.netcracker.outfit.Outfit;
import java.util.ArrayList;


import com.netcracker.user.Client;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class NewOrder implements Menu {

  private final OrderServices orderServices;
  private final ServicesFactory servicesFactory;

  public NewOrder(ServicesFactory servicesFactory) {
    this.servicesFactory = servicesFactory;
    this.orderServices = servicesFactory.getFactory().getOrderServices();
  }

  @Override
  public void preMessage(String parentsName) {
    log.info("Enter 1 to leave");
    log.info("Enter 2 to create order ");
  }

  @Override
  public void run(Scanner in, String parentsName) throws IOException {
    UUID orderUUID;
    this.preMessage(parentsName);
    label:
    while (true) {
      switch (in.next()) {
        case "1": {
          break label;
        }
        case "2": {
          log.info("Enter 1-yes. 2-not ");
          if (in.next().equalsIgnoreCase("1")) {
            orderUUID = UUID.randomUUID();
            Order order = Order.builder()
                .id(orderUUID)
                .stateOrder(VALIDATOR_INSTRUMENTS.orderState(in))
                .outfits(new ArrayList<>())
                .createdDate(new Date())
                .updatedDate(new Date())
                .label(new ArrayList<>())
                .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
                .build();
            orderServices.addOrder(order);
          }
          break label;
        }
        default: {
          this.preMessage(parentsName);
          break;
        }
      }
    }
  }

  public void createOrder(Scanner in, Client client, UUID idCar) throws IOException {
    UUID orderUUID;
    this.print();
    label:
    while (true) {
      switch (in.next()) {
        case "1": {
          break label;
        }
        case "2": {
          if (client != null && client.getId() != null) {
            log.info("Create an order with a customer?:Login {}, name:{}, phone:{}",
                client.getLogin(), client.getName(), client.getPhone());
            log.info("Enter 1-yes. 2-not ");
            if (in.next().equalsIgnoreCase("1")) {
              orderUUID = UUID.randomUUID();
              Order order = Order.builder()
                  .id(orderUUID)
                  .clientUUID(client.getId())
                  .stateOrder(VALIDATOR_INSTRUMENTS.orderState(in))
                  .outfits(new ArrayList<>())
                  .createdDate(new Date())
                  .idCar(idCar)
                  .updatedDate(new Date())
                  .label(new ArrayList<>())
                  .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
                  .build();
              log.info("Outfit data:");
              CreateOutfit createOutfit = new CreateOutfit(servicesFactory);
              Outfit outfit= createOutfit.createOutfit(in,
                  "", orderUUID);
              order.setOutfits(new ArrayList<>());
              order.getOutfits().add(outfit.getOrder());
              orderServices.addOrder(order);
              VALIDATOR_INSTRUMENTS.successfullyMessages(
                  servicesFactory.getFactory().getOutfitServices()
                      .addObjectInOutfits(outfit));
            }
          }
          break label;
        }
        default: {
          this.print();
          break;
        }
      }
    }

  }

  public void print() {
    log.info("Enter 1 to leave");
    log.info("Enter 2 to create order");
  }

}