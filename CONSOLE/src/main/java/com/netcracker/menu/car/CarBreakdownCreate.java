package com.netcracker.menu.car;

import com.netcracker.CarBreakdownServices;
import com.netcracker.breakdown.CarBreakdown;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class CarBreakdownCreate implements Menu {

 private final CarBreakdownServices carBreakdownServices;

 public CarBreakdownCreate( ServicesFactory servicesFactory) {
  this.carBreakdownServices = servicesFactory.getFactory().getCarBreakdownServices();
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
 this.preMessage(parentsName);
 }


 public void run(UUID carUUID,Scanner in, String parentsName) throws IOException {
  CarBreakdown carBreakdown = CarBreakdown.builder()
          .id(UUID.randomUUID())
          .description(VALIDATOR_INSTRUMENTS.validateDescription(in))
          .state(VALIDATOR_INSTRUMENTS.orderStateCarBr(in))
          .carClient(carUUID)
          .location(VALIDATOR_INSTRUMENTS.validateDescription(in))
          .runCarSize(VALIDATOR_INSTRUMENTS.validateMileage(in))
          .build();
  VALIDATOR_INSTRUMENTS.successfullyMessages(carBreakdownServices.addBreakdown(carBreakdown));
 }

 @Override
 public void preMessage(String parentsName) {
  log.info(parentsName);
 }

}
