package com.netcracker.menu.car;


import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.menu.order.master.ListMaster;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.outfit.Outfit;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import com.netcracker.outfit.State;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class CreateOutfit implements Menu {

  private final ServicesFactory servicesFactory;

  public CreateOutfit(ServicesFactory servicesFactory) {
    this.servicesFactory = servicesFactory;
  }

  @Override
  public void run(Scanner in, String parentsName) throws IOException {
    this.preMessage(parentsName);
  }

  @SneakyThrows
  public Outfit createOutfit(Scanner in, String parentsName, UUID orderUUID) {
    Outfit outfit = Outfit.builder()
        .id(UUID.randomUUID())
        .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
        .stateOutfit(State.RECORDED.getId())
        .name(VALIDATOR_INSTRUMENTS.validateNameOutfit(in))
        .price(VALIDATOR_INSTRUMENTS.validatePrice(in))
        .order(orderUUID)
        .dateEnt(VALIDATOR_INSTRUMENTS.getDate(in))
        .dateStart(VALIDATOR_INSTRUMENTS.getDate(in))
        .build();
    log.info("appoint master.");
    ListMaster listMaster = new ListMaster(servicesFactory);
    listMaster.run(in, "");
    outfit.setEmployer(listMaster.getMaster(in, "").getId());
    log.info("Outfit data:");
    return outfit;
  }

  @Override
  public void preMessage(String parentsName) {
    log.info(parentsName);
  }

}
