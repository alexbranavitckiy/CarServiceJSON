package com.netcracker.factory;

import com.netcracker.*;
import com.netcracker.file.services.impl.breakdown.CarBreakdownServicesImpl;
import com.netcracker.jdbc.services.impl.CarBreakdownDaoImpl;
import com.netcracker.jdbc.services.impl.breakdown.CarBreakdownDaoServicesImpl;
import com.netcracker.jdbc.services.impl.car.CarDaoServicesImpl;
import com.netcracker.jdbc.services.impl.client.ClientDaoServicesImpl;
import com.netcracker.jdbc.services.impl.login.LoginDaoServicesImpl;
import com.netcracker.jdbc.services.impl.master.MasterDaoServicesImpl;
import com.netcracker.jdbc.services.impl.masterReceiver.MasterReceiverDaoServicesImpl;
import com.netcracker.jdbc.services.impl.order.OrderDaoServicesImpl;
import com.netcracker.jdbc.services.impl.outfit.OutfitsDaoServicesImpl;

public class JDBCDaoFactory implements DaoFactory {

 @Override
 public ClientServices getClientServices() {
  return new ClientDaoServicesImpl();
 }

 @Override
 public CarServices getCarServices() {
  return new CarDaoServicesImpl();
 }

 @Override
 public MasterServices getMasterServices() {
  return new MasterDaoServicesImpl();
 }

 @Override
 public LoginServices getLoginServices() {
  return new LoginDaoServicesImpl();
 }

 @Override
 public OutfitsServices getOutfitServices() {
  return new OutfitsDaoServicesImpl();
 }

 @Override
 public OrderServices getOrderServices() {
  return new OrderDaoServicesImpl();
 }

 @Override
 public MasterReceiverServices getMasterReceiverServices() {
  return new MasterReceiverDaoServicesImpl();
 }

 @Override
 public CarBreakdownServices getCarBreakdownServices() {
  return new CarBreakdownDaoServicesImpl();
 }

}
