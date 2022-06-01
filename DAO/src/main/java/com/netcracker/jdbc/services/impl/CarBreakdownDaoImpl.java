package com.netcracker.jdbc.services.impl;

import com.netcracker.breakdown.CarBreakdown;
import com.netcracker.errors.PersistException;
import com.netcracker.jdbc.services.CarBreakdownDao;
import com.netcracker.jdbc.services.TemplateJDBCDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarBreakdownDaoImpl extends TemplateJDBCDao<CarBreakdown, UUID> implements CarBreakdownDao {

 @Override
 public String getSelectQuery() {
  return "SELECT * FROM public.car_breakdowns;";
 }

 @Override
 public String getSelectByIdQuery() {
  return "SELECT * FROM public.car_breakdowns where id=?;";
 }

 @Override
 public String getCreateQuery() {
  return "INSERT INTO public.car_breakdowns(id, descriptions, name,id_car, locationd, run_car_size, state_id) VALUES (?,?, ?, ?, ?, ?, ?);";
 }

 @Override
 public String getAllCarBreakdownsById() {
  return "SELECT * FROM public.car_breakdowns where id_car=?;";
 }

 @Override
 public String getUpdateQuery() {
  return "UPDATE public.car_breakdowns SET id=?, descriptions=?, name=?,id_car=?, locationd=?, run_car_size=?, state_id=?    where  id=?;";
 }

 @Override
 public String getDeleteQuery() {
  return "DELETE FROM public.car_breakdowns WHERE id=?;";
 }

 @Override
 protected List<CarBreakdown> parseResultSet(ResultSet rs) throws PersistException {
  List<CarBreakdown> carClients = new ArrayList<>();
  try {
   while (rs.next()) {
    CarBreakdown carClient = CarBreakdown.builder()
     .id(UUID.fromString(rs.getString("id")))
     .state(UUID.fromString(rs.getString("state_id")))
     .runCarSize(rs.getString("run_car_size"))
     .location(rs.getString("locationd"))
     .description(rs.getString("descriptions"))
     .carClient(UUID.fromString(rs.getString("id_car")))
     .build();
    carClients.add(carClient);
   }
   return carClients;
  } catch (Exception e) {
   throw new PersistException(e);
  }

 }


 @Override
 protected void prepareStatementForInsert(PreparedStatement preparedStatement, CarBreakdown carBreakdown)
  throws PersistException {
  try {
   addQuery(preparedStatement, carBreakdown);
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 @Override
 protected void prepareStatementForUpdate(PreparedStatement preparedStatement, CarBreakdown carBreakdown)
  throws PersistException {
  try {
   addQuery(preparedStatement, carBreakdown);
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 private void addQuery(PreparedStatement preparedStatement, CarBreakdown carBreakdown) throws SQLException {
  preparedStatement.setObject(1, carBreakdown.getId());
  preparedStatement.setObject(2, carBreakdown.getDescription());
  preparedStatement.setObject(3, "");
  preparedStatement.setObject(4, carBreakdown.getCarClient());
  preparedStatement.setObject(5, carBreakdown.getLocation());
  preparedStatement.setObject(6, carBreakdown.getRunCarSize());
  preparedStatement.setObject(7, carBreakdown.getState());
 }


}