package com.netcracker.jdbc.services.impl;

import com.netcracker.errors.PersistException;
import com.netcracker.jdbc.services.OrderDao;
import com.netcracker.jdbc.services.TemplateJDBCDao;
import com.netcracker.order.Order;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class OrderDaoImpl extends TemplateJDBCDao<Order, UUID> implements OrderDao {

 @Override
 public String getSelectQuery() {
  return "SELECT * FROM public.orders;";
 }

 @Override
 public String getSelectByIdQuery() {
  return "SELECT * FROM public.orders where id=?;";
 }

 @Override
 public String getSelectByIdCarQuery() {
  return "SELECT * FROM public.orders where id_car=?;";
 }

 @Override
 public String getRequestQueryByRepairRequest() {
  return "INSERT INTO public.orders(id, descriptions, id_car, id_masters, created_date,id_outfits, updated_date, id_state_order) VALUES (?, ?, ?, ?,  ?, ?, ?, ?);";
 }

 @Override
 public String getCreateQuery() {
  return "INSERT INTO public.orders(id, descriptions, id_car, id_masters, created_date, id_outfits, updated_date, id_state_order) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
 }

 @Override
 public String getAllOrderByState() {
  return "SELECT * FROM public.orders where id_state_order=?;";
 }

 @Override
 public String deleteOrderByIdCar() {
  return "DELETE FROM public.orders WHERE id_car=?;";
 }

 @Override
 public String getUpdateQuery() {
  return "UPDATE public.orders SET id=?, descriptions=?, id_car=?, id_masters=?, created_date=?, id_outfits=?, updated_date=?, id_state_order=?  WHERE id=?;";
 }

 @Override
 public String getDeleteQuery() {
  return "DELETE FROM public.outfits WHERE id=?;";
 }

 @Override
 protected List<Order> parseResultSet(ResultSet rs) throws PersistException {
  List<Order> masters = new ArrayList<>();
  try {
   while (rs.next()) {
    Order master = Order.builder()
     .id(UUID.fromString(rs.getString("id")))
     .description(rs.getString("descriptions"))
     .idCar((UUID) rs.getObject("id_car"))
     .masterReceiver((UUID) rs.getObject("id_masters"))
     .createdDate((Date) rs.getObject("created_date"))
     .updatedDate((Date) rs.getObject("updated_date"))
     .stateOrder((UUID) rs.getObject("id_state_order"))
     .build();
    if (rs.getObject("id_outfits") != null) {
     master.setOutfits(List.of((UUID) rs.getObject("id_outfits")));
    }

    masters.add(master);
   }
   return masters;
  } catch (Exception e) {
   log.warn("Entity parsing error:{}", e.getMessage());
   throw new PersistException(e);
  }
 }

 @Override
 protected void prepareStatementForInsert(PreparedStatement statement, Order outfit) {
  addQuery(statement, outfit);
 }

 private void addQuery(PreparedStatement statement, Order outfit) {
  try {
   statement.setObject(1, outfit.getId());
   statement.setObject(2, outfit.getDescription());
   statement.setObject(3, outfit.getIdCar());
   statement.setObject(4, outfit.getMasterReceiver());
   statement.setString(5, outfit.getCreatedDate().toString());
   statement.setString(7, outfit.getCreatedDate().toString());
   statement.setObject(8, outfit.getStateOrder());
   if (outfit.getOutfits() != null && !outfit.getOutfits().isEmpty()) {
    statement.setObject(6, outfit.getOutfits().get(0));
   } else {
    statement.setObject(6, null);
   }
  } catch (Exception e) {
   log.warn("Entity parsing error:{}", e.getMessage());
  }
 }

 @Override
 protected void prepareStatementForUpdate(PreparedStatement statement, Order order) {
  addQuery(statement, order);
  try {
   statement.setObject(9, order.getId());
  } catch (Exception e) {
   log.warn("Entity parsing error:{}", e.getMessage());
  }
 }

}
