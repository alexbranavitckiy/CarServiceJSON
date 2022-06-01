package com.netcracker.jdbc.services.impl.car;

import com.netcracker.CarServices;
import com.netcracker.errors.PersistException;
import com.netcracker.jdbc.services.CarDao;
import com.netcracker.jdbc.services.impl.CarClientDaoImpl;
import com.netcracker.marka.CarClient;
import com.netcracker.order.State;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CarDaoServicesImpl implements CarServices {

 private final CarDao carDao = new CarClientDaoImpl();

 @Override
 public List<CarClient> getCarByIdClient(UUID uuidClient) {
  try {
   return carDao.getAllByQuery(uuidClient, carDao.getSelectAllByIdClient());
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return new ArrayList<>();
 }

 @Override
 public List<CarClient> getAllCar() {
  try {
   return carDao.getAll();
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return new ArrayList<>();
 }

 @Override
 public boolean addCar(CarClient carClient) {
  try {
   return this.carDao.addObject(carClient);
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return false;
 }

 @Override
 @SneakyThrows
 public boolean updateCarClient(CarClient carClient) {
  try {
   return this.carDao.update(carClient);
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return false;
 }


 @Override
 public List<CarClient> getAllCarClientWaitState(State state, UUID uuidClient) {
  try {
   return carDao.getAllByAnyQuery(carDao.getAllCarClientWaitState(),uuidClient, state.getId());
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return new ArrayList<>();
 }

 @Override
 public List<CarClient> getAllCarClientWithState(State state) {
  try {
   return carDao.getAllByAnyQuery(carDao.getAllCarWithState(), state.getId());
  } catch (PersistException p) {
   log.warn("Error getting data car:{}", p.getMessage());
  }
  return new ArrayList<>();
 }
}
