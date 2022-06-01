package com.netcracker.menu.order.master;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.MasterServices;
import com.netcracker.user.Master;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListMaster implements Menu {

  private final ServicesFactory servicesFactory;

  public ListMaster(ServicesFactory servicesFactory) {
    this.servicesFactory = servicesFactory;
  }

  @Override
  public void preMessage(String parentsName) {
    log.info("Enter 2 Display a list of master.");
  }

  @Override
  public void run(Scanner in, String parentsName) throws IOException {
    this.preMessage(parentsName);
  }

  public Master getMaster(Scanner in, String parentsName) throws IOException, EmptySearchException {
    MasterServices masterServices = servicesFactory.getFactory().getMasterServices();
    this.preMessage(parentsName);
    label:
    while (true) {
      switch (in.next()) {
        case "2": {
          try {
            List<Master> masters = masterServices.getAllMaster();
            for (int x = 0; x < masters.size(); x++) {
              log.info("id[{}] Login:{} Description:{} Mail:{} Phone:{} Qualification:{} Role:{}",
                  x + 1,
                  masters.get(x).getLogin(), masters.get(x).getDescription(),
                  masters.get(x).getMail(), masters.get(x).getPhone(),
                  masters.get(x).getQualification(), masters.get(x).getRole());
            }
            log.info("Enter Master ID");
            return masters.get(in.nextInt() - 1);
          } catch (EmptySearchException e) {
            log.info("The search has not given any results:{}", e.getMessage());
          } catch (IndexOutOfBoundsException e) {
            log.info("Selected master not found");
          }
          break label;
        }
        default: {
          this.preMessage(parentsName);
          break;
        }
      }
    }
    throw new EmptySearchException("Selected master not found");
  }


}