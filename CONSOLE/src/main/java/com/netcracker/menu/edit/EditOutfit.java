package com.netcracker.menu.edit;

import com.netcracker.menu.Menu;
import com.netcracker.menu.validator.ValidatorInstruments;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.outfit.Outfit;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.netcracker.outfit.State;
import lombok.extern.slf4j.Slf4j;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;

@Slf4j
public class EditOutfit implements Menu {

 private final Outfit outfit;

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1 {}", parentsName);
 }

 public EditOutfit(Outfit outfit) {
  this.outfit = outfit;
 }

 @Override
 public void run(Scanner in, String parentsName) throws IOException {
  log.info("Descriptions");
  if (VALIDATOR_INSTRUMENTS.edit(this.outfit.getDescription(), in)) {
   this.outfit.setDescription(VALIDATOR_INSTRUMENTS.validateDescription(in));
  }
  log.info("Enter name outfit");
  if (VALIDATOR_INSTRUMENTS.edit(this.outfit.getName(), in)) {
   this.outfit.setName(VALIDATOR_INSTRUMENTS.validateNameOutfit(in));
  }
  log.info("Enter state outfit");
  if (VALIDATOR_INSTRUMENTS.edit(List.of(State.values()).stream()
   .filter(z -> z.getId().equals(this.outfit.getStateOutfit())).findFirst().get().name(), in)) {
   this.outfit.setStateOutfit(VALIDATOR_INSTRUMENTS.stateOutfit(in));
  }
  log.info("Enter price outfit");
  if (VALIDATOR_INSTRUMENTS.edit(String.valueOf(this.outfit.getPrice()), in)) {
   this.outfit.setPrice(VALIDATOR_INSTRUMENTS.validatePrice(in));
  }
 }

 public Outfit getOutfit() {
  return outfit;
 }
}