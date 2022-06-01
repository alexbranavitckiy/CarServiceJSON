package com.netcracker.menu.order;

import com.netcracker.OutfitsServices;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.menu.car.CreateOutfit;
import com.netcracker.menu.edit.EditOrder;
import com.netcracker.menu.validator.ValidatorInstruments;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.order.Order;
import com.netcracker.OrderServices;

import com.netcracker.outfit.Outfit;
import java.io.IOException;
import java.util.*;

import com.netcracker.order.State;
import lombok.extern.slf4j.Slf4j;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class ListOrders implements Menu {

  private final ServicesFactory servicesFactory;
  private Order order;
  private final OrderServices orderServices;

  public ListOrders(ServicesFactory servicesFactory) {
    this.orderServices = servicesFactory.getFactory().getOrderServices();
    this.servicesFactory = servicesFactory;
  }

  @Override
  public void preMessage(String parentsName) {
    log.info("Enter 1: {}", parentsName);
    log.info("Enter 2 Display a list of orders.");
    log.info("Enter 3 Display a list of orders. With request type");
  }

  @Override
  public void run(Scanner in, String parentsName) throws IOException {
    OutfitsServices outfitsServices = servicesFactory.getFactory().getOutfitServices();
    this.preMessage(parentsName);
    label:
    while (true) {
      switch (in.next()) {
        case "2": {
          try {
            List<Order> orders = orderServices.getAll();
            printOrders(orders);
            log.info("Go to order ID:");
            this.order = orders.get(in.nextInt() - 1);
            this.editOrder(in);
            log.info("Create an outfit with this order?\n1-yes.\n2-no.");
            this.createOutfit(in, outfitsServices);
            break label;
          } catch (EmptySearchException e) {
            log.warn("The search has not given any results. {}", e.getMessage());
          } catch (InputMismatchException e) {
            log.warn("Invalid data:{}. Please try again", e.getMessage());
          } catch (IndexOutOfBoundsException e) {
            log.info("Selected order not found");
          }
          this.preMessage(parentsName);
          break;
        }
        case "3": {
          try {
            List<Order> orders = orderServices.getOrderWithRequestState();
            if (orders.size() > 0) {
              this.printOrders(orders);
              log.info("Go to order ID:");
              this.order = orders.get(in.nextInt() - 1);
              this.editOrder(in);
              log.info("Create an outfit? 1-yeas. 2-no");
              this.createOutfit(in, outfitsServices);
            } else {
              log.info("No data with this status");
            }
          } catch (InputMismatchException e) {
            log.warn("Invalid data:{}. Please try again", e.getMessage());
          } catch (IndexOutOfBoundsException e) {
            log.info("Selected order not found");
          }
          this.preMessage(parentsName);
          break;
        }
        case "1": {
          break label;
        }

        default: {
          this.preMessage(parentsName);
          break;
        }
      }
    }
  }

  private void createOutfit(Scanner in, OutfitsServices outfitsServices) throws IOException {
    if (in.next().equalsIgnoreCase("1")) {
      CreateOutfit createOutfit = new CreateOutfit(servicesFactory);
      Outfit outfit = createOutfit.createOutfit(in, "Main menu", this.order.getId());
      log.info("appoint master.");
      VALIDATOR_INSTRUMENTS
          .successfullyMessages(outfitsServices.addObjectInOutfits(outfit));
      this.order.setOutfits(List.of(outfit.getId()));
      VALIDATOR_INSTRUMENTS.successfullyMessages(orderServices.updateOrder(this.order));
    }
  }

  private void printOrders(List<Order> orders) {
    Order order;
    if (orders.size() > 0) {
      for (int x = 0; x < orders.size(); x++) {
        order = orders.get(x);
        UUID uuidState = order.getStateOrder();
        log.info("Id:{} {} Created:{} {} Updated:{}"
            , x
            , List.of(State.values()).stream().filter(z -> z.getId().equals(uuidState)).findFirst()
                .get().name()
            , order.getCreatedDate()
            , order.getDescription()
            , order.getUpdatedDate()
        );
      }
    }
  }

  private void editOrder(Scanner in) throws IOException {
    log.info("Edit Order? 1-yeas. 2-no");
    if (in.next().equals("1")) {
      EditOrder editOrder = new EditOrder(this.order);
      editOrder.run(in, "Main menu");
      VALIDATOR_INSTRUMENTS.successfullyMessages(orderServices.updateOrder(editOrder.getOrder()));
      this.order = editOrder.getOrder();
    }
  }

  public Optional<Order> getOrder() {
    return Optional.of(this.order);
  }

}