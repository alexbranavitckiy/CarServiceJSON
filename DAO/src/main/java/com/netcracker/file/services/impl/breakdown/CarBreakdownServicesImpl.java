package com.netcracker.file.services.impl.breakdown;

import com.netcracker.CarBreakdownServices;
import com.netcracker.breakdown.CarBreakdown;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.file.FileService;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.services.impl.CRUDServicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CarBreakdownServicesImpl implements CarBreakdownServices {

 private final CRUDServices<CarBreakdown, UUID> crudServices = new CRUDServicesImpl<>();
 private final FileService fileService = new FileService();

 @Override
 public List<CarBreakdown> getAllBreakdown() throws EmptySearchException {
  return crudServices.getAll(fileService.getBreakdown(), CarBreakdown[].class);
 }

 @Override
 public boolean addBreakdown(CarBreakdown carBreakdown) {
  return crudServices.addObject(carBreakdown, fileService.getBreakdown(), CarBreakdown[].class);
 }

 @Override
 public Optional<CarBreakdown> getBreakdownById(UUID uuid) throws EmptySearchException {
  return crudServices.getAll(fileService.getBreakdown(), CarBreakdown[].class).stream().filter(x -> x.getId().equals(uuid)).findFirst();
 }

 @Override
 public boolean updateBreakdown(CarBreakdown carBreakdown) {
  return crudServices.updateObject(carBreakdown, fileService.getBreakdown(), CarBreakdown[].class);
 }

 @Override
 public List<CarBreakdown> getAllBreakdownByCar(UUID uuid) throws EmptySearchException {
  return crudServices.getAll(fileService.getBreakdown(), CarBreakdown[].class).stream().filter(x -> x.getCarClient().equals(uuid)).collect(Collectors.toList());

 }
}
