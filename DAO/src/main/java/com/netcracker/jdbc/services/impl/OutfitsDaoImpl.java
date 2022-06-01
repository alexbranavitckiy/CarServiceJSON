package com.netcracker.jdbc.services.impl;

import com.netcracker.errors.PersistException;
import com.netcracker.jdbc.services.OutfitDao;
import com.netcracker.jdbc.services.TemplateJDBCDao;
import com.netcracker.outfit.Outfit;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class OutfitsDaoImpl extends TemplateJDBCDao<Outfit, UUID> implements OutfitDao {

 @Override
 public String getSelectQuery() {
  return "SELECT * FROM public.outfits;";
 }

 @Override
 public String getSelectByIdQuery() {
  return "SELECT * FROM public.outfits where id=?;";
 }

 @Override
 public String getCreateQuery() {
  return "INSERT INTO public.outfits(id, name_outfits, descriptions, start_date, end_date, price_sum, state_outfits,id_master,id_orders) VALUES (?,?,?, ?, ? , ?, ?, ?, ?);";
 }

 @Override
 public String getAllByMasterAndState() {
  return "SELECT * FROM public.outfits where id_master=? and state_outfits=?;";
 }

 @Override
 public String getAllByState() {
  return "SELECT * FROM public.outfits where  state_outfits=?;";
 }

 @Override
 public String getAllByMaster() {
  return "SELECT * FROM public.outfits where id_master=?;";
 }

 @Override
 public String getAllBetweenDate() {
  return "SELECT * FROM  public.outfits WHERE start_date BETWEEN ? AND ?;";
 }


 @Override
 public String getAllSortByDateDesc() {
  return "SELECT * FROM public.outfits  ORDER BY start_date  DESC;";
 }

 @Override
 public String getUpdateQuery() {
  return "UPDATE public.outfits SET id=?, name_outfits=?, descriptions=?, start_date=?, end_date=?, price_sum=?, state_outfits=? ,id_master=?,id_orders=? where id=?;";
 }

 @Override
 public String getDeleteQuery() {
  return "DELETE FROM public.outfits WHERE id=?;";
 }

 @Override
 protected List<Outfit> parseResultSet(ResultSet rs) throws PersistException {
  List<Outfit> masters = new ArrayList<>();
  try {
   while (rs.next()) {
    Outfit master = Outfit.builder()
     .id(UUID.fromString(rs.getString("id")))
     .name(rs.getString("name_outfits"))
     .description(rs.getString("descriptions"))
     .dateStart(rs.getTimestamp("start_date"))
     .dateEnt(rs.getTimestamp("end_date"))
     .employer(UUID.fromString(rs.getString("id_master")))
     .price(rs.getDouble("price_sum"))
     .stateOutfit((UUID) rs.getObject("state_outfits"))
     .order((UUID) rs.getObject("id_orders"))
     .build();
    masters.add(master);
   }
   return masters;
  } catch (Exception e) {
   log.warn("Entity parsing error:{}", e.getMessage());
   throw new PersistException(e);
  }
 }

 @Override
 protected void prepareStatementForInsert(PreparedStatement statement, Outfit outfit)
  throws PersistException {
  addQuery(statement, outfit);
 }

 private void addQuery(PreparedStatement statement, Outfit outfit) throws PersistException {
  try {
   statement.setObject(1, outfit.getId());
   statement.setObject(2, outfit.getName());
   statement.setObject(3, outfit.getDescription());
   statement.setTimestamp(4, new Timestamp(outfit.getDateStart().getTime()));
   statement.setTimestamp(5, new Timestamp(outfit.getDateEnt().getTime()));
   statement.setObject(6, outfit.getPrice());
   statement.setObject(7, outfit.getStateOutfit());
   statement.setObject(8, outfit.getEmployer());
   statement.setObject(9, outfit.getOrder());
  } catch (Exception e) {
   log.warn("Entity parsing error:{}", e.getMessage());
   throw new PersistException(e);
  }
 }

 @Override
 protected void prepareStatementForUpdate(PreparedStatement statement, Outfit outfit)
  throws PersistException {
  addQuery(statement, outfit);
  try {
   statement.setObject(10, outfit.getId());
  } catch (Exception e) {
   log.warn("Entity parsing error:{}", e.getMessage());
  }
 }

}
