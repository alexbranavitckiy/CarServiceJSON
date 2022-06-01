package com.netcracker.factory;

import com.netcracker.*;
import com.netcracker.file.services.impl.breakdown.CarBreakdownServicesImpl;
import com.netcracker.file.services.impl.car.CarServicesImpl;
import com.netcracker.file.services.impl.client.ClientServicesImpl;
import com.netcracker.file.services.impl.login.LoginServicesImpl;
import com.netcracker.file.services.impl.master.MasterServicesImpl;
import com.netcracker.file.services.impl.masterReceiver.MasterReceiverServicesImpl;
import com.netcracker.file.services.impl.order.OrderServicesImpl;
import com.netcracker.file.services.impl.outfit.OutfitsServicesImpl;

public class FileDaoFactory implements DaoFactory {

 @Override
 public ClientServices getClientServices() {
  return new ClientServicesImpl();
 }

 @Override
 public CarServices getCarServices() {
  return new CarServicesImpl();
 }

 @Override
 public MasterServices getMasterServices() {
  return new MasterServicesImpl();
 }

 @Override
 public LoginServices getLoginServices() {
  return new LoginServicesImpl();
 }

 @Override
 public OutfitsServices getOutfitServices() {
  return new OutfitsServicesImpl();
 }

 @Override
 public OrderServices getOrderServices() {
  return new OrderServicesImpl();
 }

 @Override
 public MasterReceiverServices getMasterReceiverServices() {
  return new MasterReceiverServicesImpl();
 }

 @Override
 public CarBreakdownServices getCarBreakdownServices() {
  return new CarBreakdownServicesImpl();
 }
}
