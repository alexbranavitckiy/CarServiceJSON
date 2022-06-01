package com.netcracker.file.services.impl.order;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.marka.CarClient;
import com.netcracker.order.Order;
import com.netcracker.order.State;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.FileService;
import com.netcracker.file.services.impl.CRUDServicesImpl;
import com.netcracker.OrderServices;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServicesImpl implements OrderServices {

 private final CRUDServices<Order, UUID> searchServices = new CRUDServicesImpl<>();

 public OrderServicesImpl() {
 }

 @Override
 public boolean addOrder(Order order) {
  try {
   return (searchServices.addObject(order, new File(FileService.ORDERS_PATH), Order[].class));
  } catch (Exception e) {
   log.error("Error adding object", e);
  }
  return false;
 }

 @Override
 public List<Order> getAll() throws EmptySearchException {
  return searchServices.getAll(new File(FileService.ORDERS_PATH), Order[].class);
 }

 @Override
 @SneakyThrows
 public List<Order> getOrderWithRequestState() {
  return searchServices.getAll(new File(FileService.ORDERS_PATH), Order[].class).stream()
   .filter(x -> x.getStateOrder().equals(State.REQUEST.getId())).collect(
    Collectors.toList());
 }

 @Override
 public boolean updateOrder(Order order) {
  return searchServices.updateObject(order, new File(FileService.ORDERS_PATH), Order[].class);
 }

 @Override
 public boolean repairRequest(Order order) {
  try {
   return (searchServices.addObject(order, new File(FileService.ORDERS_PATH), Order[].class));
  } catch (Exception e) {
   log.error("Error adding object", e);
  }
  return false;
 }

 @Override
 public boolean cancelRequest(UUID uuidCar) {
  try {
   return (searchServices
    .deleteObjectById(searchServices.getAll(new File(FileService.ORDERS_PATH), Order[].class)
     .stream().filter(x -> x.getIdCar().equals(uuidCar)).findFirst()
     .get(), new File(FileService.ORDERS_PATH), Order[].class));
  } catch (Exception e) {
   log.error("Error adding object", e);
  }
  return false;
 }

 @Override
 @SneakyThrows
 public Optional<Order> getOrderByIdCar(UUID car) {
  return searchServices.getAll(new File(FileService.ORDERS_PATH), Order[].class)
   .stream().filter(x -> x.getIdCar().equals(car)).findFirst();
 }

 @Override
 @SneakyThrows
 public Optional<Order> getOrderById(UUID uuid) {
  return  searchServices.getAll(new File(FileService.ORDERS_PATH), Order[].class)
   .stream().filter(x -> x.getId().equals(uuid)).findFirst();
 }
}
