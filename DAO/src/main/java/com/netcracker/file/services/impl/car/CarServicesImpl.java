package com.netcracker.file.services.impl.car;

import com.netcracker.CarServices;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.file.FileService;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.services.impl.CRUDServicesImpl;
import com.netcracker.marka.CarClient;
import com.netcracker.order.Order;
import com.netcracker.order.State;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class CarServicesImpl implements CarServices {

 private final FileService fileService = new FileService();
 private final CRUDServices<CarClient, UUID> crudServices = new CRUDServicesImpl<>();
 private final CRUDServices<Order, UUID> orderUUIDCRUDServices = new CRUDServicesImpl<>();


 @Override
 public List<CarClient> getCarByIdClient(UUID uuidClient) throws EmptySearchException {
  return crudServices.getAll(fileService.getCar(), CarClient[].class)
   .stream().filter(x -> {
    if (x.getId_clients() != null)
     return x.getId_clients().equals(uuidClient);
    return false;
   }).collect(Collectors.toList());
 }

 @Override
 public List<CarClient> getAllCar() throws EmptySearchException {
  return crudServices.getAll(fileService.getCar(), CarClient[].class);
 }

 @Override
 public boolean addCar(CarClient client) {
  return crudServices.addObject(client, fileService.getCar(), CarClient[].class);
 }

 @Override
 public boolean updateCarClient(CarClient carClient) {
  return crudServices.updateObject(carClient, fileService.getCar(), CarClient[].class);
 }

 @Override
 @SneakyThrows
 public List<CarClient> getAllCarClientWaitState(State state, UUID uuidClient) {
  try {
   List<CarClient> carClients = crudServices.getAll(new File(FileService.CAR_PATH), CarClient[].class)
    .stream().filter(x -> {
     if (x != null) return x.getId_clients().equals(uuidClient);
     return false;
    }).collect(Collectors.toList());
   return getCarClients(state, carClients);
  } catch (NullPointerException e) {
   log.debug("Read error:{}", e.getMessage());
   return new ArrayList<>();
  }
 }

 @Override
 @SneakyThrows
 public List<CarClient> getAllCarClientWithState(State state) {
  List<CarClient> carClients = crudServices.getAll(new File(FileService.CAR_PATH), CarClient[].class);
  return getCarClients(state, carClients);
 }

 private List<CarClient> getCarClients(State state, List<CarClient> carClients) throws EmptySearchException {
  List<UUID> list = orderUUIDCRUDServices.getAll(new File(FileService.ORDERS_PATH), Order[].class).stream()
   .filter(x -> x.getStateOrder().equals(state.getId())).map(Order::getIdCar).collect(
    Collectors.toList());
  return carClients.stream().filter(x ->
   list.stream().anyMatch(z -> x.getId().equals(z))).collect(Collectors.toList());
 }

}
