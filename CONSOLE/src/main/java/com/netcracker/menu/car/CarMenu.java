package com.netcracker.menu.car;

import com.netcracker.CarBreakdownServices;
import com.netcracker.CarServices;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.marka.CarClient;
import com.netcracker.menu.Menu;
import com.netcracker.menu.edit.EditCar;
import com.netcracker.ClientServices;
import com.netcracker.session.UserSession;
import com.netcracker.user.Client;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class CarMenu implements Menu {

  private final ClientServices clientServices;
  private final CarServices carServices;
  private final CarBreakdownServices carBreakdownServices;

  public CarMenu(ServicesFactory servicesFactory) {
    this.carBreakdownServices = servicesFactory.getFactory().getCarBreakdownServices();
    this.clientServices = servicesFactory.getFactory().getClientServices();
    this.carServices = servicesFactory.getFactory().getCarServices();
  }

  @Override
  public void preMessage(String nameMenu) {
    log.info("Enter 1. {}", nameMenu);
    log.info("Enter 2. Display a list of cars");
    log.info("Enter 5. Select a car to edit");
    log.info("Enter 6. Add car data");
    log.info("Enter 7. Select a car and show repair history");
  }

  @Override
  @SneakyThrows
  public void run(Scanner in, String parentsName) throws IOException {
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
              List<CarClient> carClient = carServices.getCarByIdClient(x.getId());
              if (carClient.size() > 0) {
                carClient.forEach(z -> log.info(String.valueOf(z)));
                log.info("Enter 1.Close selection and editor menu");
                log.info("Enter 2. Display a list of cars");
                log.info("Enter 5 to go to a specific car");
              } else {
                log.info("No car data found");
                log.info("Enter 1.Close selection and editor menu");
                log.info("Enter 6 to add car data");
              }
            } catch (EmptySearchException e) {
              this.preMessage(parentsName);
              log.info("Data not found:{}", e.getLocalizedMessage());
            }
          });
          break;
        }
        case "5": {
          UserSession.getClientSession().ifPresent(x -> {
            try {
              List<CarClient> carClients = carServices.getCarByIdClient(x.getId());
              if (x.getCarClients().size() > 0) {
                for (int z = 0; z < carClients.size(); z++) {
                  log.info("id:{} Car:{}", z + 1, carClients.get(z));
                }
                log.info("Enter car id");
                int metadataCar = in.nextInt();
                log.info("Edit selected car?\nEnter 1 to continue editing\n\"Enter 2 go out");
                if (!in.next().equalsIgnoreCase("2")) {
                  try {
                    EditCar editCar = new EditCar(carClients.get(metadataCar - 1));
                    editCar.run(in, "Car menu");
                    if (carServices.updateCarClient(editCar.getCarClient())) {
                      log.info("Data entered successfully");
                    } else {
                      log.info("An input error occurred while entering data. Retry data change");
                    }
                  } catch (IOException e) {
                    e.printStackTrace();
                  } catch (IndexOutOfBoundsException e) {
                    log.info("Invalid data entered please try again");
                  }
                }
                this.preMessage(parentsName);
              } else {
                log.info("No car data found.");
                log.info("Enter 1.Close selection and editor menu");
                log.info("Enter 6 to add car data");
              }
            } catch (EmptySearchException e) {
              log.info("Data not found");
            }
          });
          break;
        }
        case "6": {
          CreateCarClient creatCarClient = new CreateCarClient();
          CarClient carClient = creatCarClient
              .createCar(in, "", UserSession.getClientSession().get().getId());
          Client client = UserSession.getCloneClientSession();
          if (client.getCarClients() != null) {
            client.getCarClients().add(carClient.getId());
          }
          if (clientServices.updateClient(client) && carServices.addCar(carClient)) {
            log.info("Data added successfully");
          } else {
            System.out.println("An error occurred while entering data, please try again");
          }
          this.preMessage(parentsName);
          break;
        }
        case "7": {
          UserSession.getClientSession().ifPresent(x -> {
            try {
              List<CarClient> carClients = carServices.getCarByIdClient(x.getId());
              if (x.getCarClients().size() > 0) {
                for (int z = 0; z < carClients.size(); z++) {
                  log.info("id:{} Car:{}", z + 1, carClients.get(z));
                }
                log.info("Enter car id");
                int metadataCar = in.nextInt();
                log.info("Display crash history?\n1-yeas\n\"2-no");
                if (!in.next().equalsIgnoreCase("2")) {
                  try {
                    carBreakdownServices.getBreakdownById(carClients.get(metadataCar - 1).getId());
                  } catch (IndexOutOfBoundsException e) {
                    log.info("Invalid data entered please try again");
                  }
                }
                this.preMessage(parentsName);
              } else {
                log.info("No car data found.");
                log.info("Enter 1.Close selection and editor menu");
                log.info("Enter 6 to add car data");
              }
            } catch (EmptySearchException e) {
              log.info("Data not found");
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
