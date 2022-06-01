package com.netcracker.menu.order.outfit;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.factory.ServicesFactory;
import com.netcracker.menu.Menu;
import com.netcracker.menu.edit.EditOutfit;
import com.netcracker.menu.validator.ValidatorInstruments;
import com.netcracker.menu.validator.ValidatorInstrumentsImpl;
import com.netcracker.outfit.Outfit;
import com.netcracker.OutfitsServices;

import java.io.IOException;
import java.util.*;

import com.netcracker.outfit.State;
import lombok.extern.slf4j.Slf4j;

import static com.netcracker.menu.validator.ValidatorInstrumentsImpl.VALIDATOR_INSTRUMENTS;


@Slf4j
public class ListOutfit implements Menu {

 private final ServicesFactory servicesFactory;
 private Outfit outfit;


 public ListOutfit(ServicesFactory servicesFactory) {
  this.servicesFactory = servicesFactory;
 }

 @Override
 public void preMessage(String parentsName) {
  log.info("Enter 1: {}", parentsName);
  log.info("Enter 2 Show all outfits.");
  log.info("Enter 3 Show outfit by time.");
  log.info("Enter 4 Select and edit.");
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
      this.print(outfitsServices.getAllOutfits(), in);
     } catch (EmptySearchException e) {
      log.warn("The search has not given any results. {}", e.getMessage());
     } catch (InputMismatchException e) {
      log.warn("Invalid data:{}. Please try again", e.getMessage());
     } catch (IndexOutOfBoundsException e) {
      log.info("Selected  not found:{}", e.getMessage());
     }
     this.preMessage(parentsName);
     break;
    }
    case "3": {
     try {
      log.info("Enter the time interval");
      this.print(outfitsServices.getAllOutfitsByData(VALIDATOR_INSTRUMENTS.getDate(in).toString(),
       VALIDATOR_INSTRUMENTS.getDate(in).toString()), in);
     } catch (InputMismatchException e) {
      log.warn("Invalid data:{}. Please try again", e.getMessage());
     } catch (IndexOutOfBoundsException e) {
      log.info("Selected  not found:{}", e.getMessage());
     }
     this.preMessage(parentsName);
     break;
    }
    case "1": {
     break label;
    }
    case "4": {
     try {
      this.print(outfitsServices.getAllOutfits(), in);
      EditOutfit editOutfit = new EditOutfit(this.outfit);
      editOutfit.run(in, "Main menu");
      log.info("Enter dateState");
      if (VALIDATOR_INSTRUMENTS.edit(String.valueOf(this.outfit.getDateStart()), in)) {
       this.outfit.setDateStart(VALIDATOR_INSTRUMENTS.getDate(in));
      }
      log.info("Enter dateEnd");
      if (VALIDATOR_INSTRUMENTS.edit(String.valueOf(this.outfit.getDateEnt()), in)) {
       this.outfit.setDateEnt(VALIDATOR_INSTRUMENTS.getDate(in));
      }
      VALIDATOR_INSTRUMENTS.successfullyMessages(outfitsServices.updateOutfit(this.outfit));
     } catch (EmptySearchException e) {
      log.warn("The search has not given any results. {}", e.getMessage());
     } catch (InputMismatchException e) {
      log.warn("Invalid data:{}. Please try again", e.getMessage());
     } catch (IndexOutOfBoundsException e) {
      log.info("Selected  not found:{}", e.getMessage());
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

 private void print(List<Outfit> outfitList, Scanner in) {
  if (outfitList.size() > 0) {
   for (int x = 0; x < outfitList.size(); x++) {
    UUID uuidState=outfitList.get(x).getStateOutfit();
    log.info(
     "Id[{}] DateStart: {} DateEnt: {} StateOutfit: {} Descriptions: {} Name: {}. ",
     x + 1, outfitList.get(x).getDateStart(), outfitList.get(x).getDateEnt(),
     List.of(State.values()).stream().filter(z -> z.getId().equals(uuidState)).findFirst().get().name()
    , outfitList.get(x).getDescription(),
     outfitList.get(x).getName());
   }
   log.info("Enter outfit id");
   this.outfit = outfitList.get(in.nextInt() - 1);
  } else {
   log.info("Outfit that doesn't exist");
  }
 }

 public Optional<Outfit> getOutfit() {
  return Optional.of(this.outfit);
 }
}