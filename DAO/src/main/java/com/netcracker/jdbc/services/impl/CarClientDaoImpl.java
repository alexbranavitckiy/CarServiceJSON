package com.netcracker.jdbc.services.impl;

import com.netcracker.errors.PersistException;
import com.netcracker.jdbc.services.CarDao;
import com.netcracker.jdbc.services.TemplateJDBCDao;
import com.netcracker.marka.CarClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarClientDaoImpl extends TemplateJDBCDao<CarClient, UUID> implements CarDao {

 @Override
 public String getSelectQuery() {
  return "SELECT * FROM public.car_clients;";
 }

 @Override
 public String getSelectByIdQuery() {
  return "SELECT * FROM public.car_clients where id=?;";
 }

 @Override
 public String getSelectAllByIdClient() {
  return "SELECT * FROM public.car_clients where id_clients=?;";
 }

 @Override
 public String getAllCarClientWaitState() {
  return "SELECT   car_clients.id, id_clients, id_breakdown, summary, ear, run, metadata_car, car_clients.descriptions FROM public.car_clients, public.orders where car_clients.id_clients=?  AND orders.id_state_order =?";
 }

 @Override
 public String getAllCarWithState() {
  return "SELECT   car_clients.id, id_clients, id_breakdown, summary, ear, run, metadata_car, car_clients.descriptions FROM public.car_clients, public.orders where orders.id_state_order =?";
 }

 @Override
 public String getCreateQuery() {
  return " INSERT INTO public.car_clients(id, id_clients, summary, ear, run, metadata_car  , descriptions) VALUES ( ?, ?, ?, ?, ?, ?, ?);";
 }

 @Override
 public String getUpdateQuery() {
  return "UPDATE public.car_clients SET id=?, id_clients=?, summary=?, ear=?, run=?, metadata_car =? , descriptions=?   where  id=?;";
 }

 @Override
 public String getDeleteQuery() {
  return "DELETE FROM public.car_clients WHERE id=?;";
 }


 @Override
 protected List<CarClient> parseResultSet(ResultSet rs) throws PersistException {
  List<CarClient> carClients = new ArrayList<>();
  try {
   while (rs.next()) {
    CarClient carClient = CarClient.builder()
     .id(UUID.fromString(rs.getString("id")))
     .summary(rs.getString("summary"))
     .ear(rs.getString("ear"))
     .run(rs.getString("run"))
     .metadataCar(rs.getString("metadata_car"))
     .description(rs.getString("descriptions"))
     .id_clients((UUID) rs.getObject("id_clients"))
     .build();
    carClients.add(carClient);
   }
   return carClients;
  } catch (Exception e) {
   throw new PersistException(e);
  }

 }


 @Override
 protected void prepareStatementForInsert(PreparedStatement preparedStatement, CarClient carClient)
  throws PersistException {
  try {
   addQuery(preparedStatement, carClient);
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 @Override
 protected void prepareStatementForUpdate(PreparedStatement preparedStatement, CarClient carClient)
  throws PersistException {
  try {
   addQuery(preparedStatement, carClient);
   preparedStatement.setObject(8, carClient.getId());
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 private void addQuery(PreparedStatement preparedStatement, CarClient carClient) throws SQLException {
  preparedStatement.setObject(1, carClient.getId());
  preparedStatement.setObject(2, carClient.getId_clients());
  preparedStatement.setObject(3, carClient.getSummary());
  preparedStatement.setObject(4, Date.valueOf(carClient.getEar()));
  preparedStatement.setObject(5, carClient.getRun());
  preparedStatement.setObject(6, carClient.getMetadataCar());
  preparedStatement.setObject(7, carClient.getDescription());
 }


}