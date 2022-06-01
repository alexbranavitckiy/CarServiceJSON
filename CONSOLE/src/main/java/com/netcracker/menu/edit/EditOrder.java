package com.netcracker.menu.edit;

import com.netcracker.menu.Menu;
import com.netcracker.menu.validator.ValidatorInstruments;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.order.Order;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.netcracker.order.State;
import lombok.extern.slf4j.Slf4j;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class EditOrder implements Menu {

 private final Order order;

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1 {}", parentsName);
 }

 public EditOrder(Order order) {
  this.order = order;
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  log.info("Descriptions");
  if (VALIDATOR_INSTRUMENTS.edit(this.order.getDescription(), in)) {
   this.order.setDescription(VALIDATOR_INSTRUMENTS.validateDescription(in));
  }
  Optional<State> state = List.of(State.values()).stream().filter(x -> x.getId().equals(this.order.getStateOrder())).findFirst();
  log.info("State order");
  if (state.isPresent()) {
   if (VALIDATOR_INSTRUMENTS.edit(state.get().toString(), in)) {
    this.order.setStateOrder(VALIDATOR_INSTRUMENTS.orderState(in));
   }
  } else {
   this.order.setStateOrder(VALIDATOR_INSTRUMENTS.orderState(in));
  }
  this.order.setUpdatedDate(new Date());
 }

 public Order getOrder() {
  return order;
 }
}