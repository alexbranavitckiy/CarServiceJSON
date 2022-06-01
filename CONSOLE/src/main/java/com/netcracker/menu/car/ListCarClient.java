package com.netcracker.menu.car;

import com.netcracker.CarServices;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.marka.CarClient;
import com.netcracker.menu.Menu;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ListCarClient implements Menu {

  private final CarServices carServices;

  public ListCarClient(ServicesFactory servicesFactory) {
    this.carServices = servicesFactory.getFactory().getCarServices();
  }

  @Override
  public void preMessage(String parentsName) {
    log.info("Enter 1: {}", parentsName);
    log.info("Enter 2 Display a list of car.");
  }

  @Override
  public void run(Scanner in, String parentsName) throws IOException {
    this.preMessage(parentsName);
  }

  public CarClient run(Scanner in, String parentsName, UUID uuid) throws IOException {
    List<CarClient> clientList;
    try {
      clientList = carServices.getCarByIdClient(uuid);
      if (clientList.size() > 0) {
        for (int x = 1; x < clientList.size() + 1; x++) {
          log.info("Id:{} {} ", x, clientList.get(x - 1));
        }
        log.info("Enter car id");
        return  clientList.get(in.nextInt() - 1);
      }
    } catch (InputMismatchException e) {
      log.warn("Invalid data:{}. Please try again", e.getMessage());
    } catch (IndexOutOfBoundsException e) {
      log.info("Selected client not found");
    } catch (EmptySearchException e) {
      log.warn("The search has not given any results. {}", e.getMessage());
    }
    throw new  EmptyStackException();
  }

}