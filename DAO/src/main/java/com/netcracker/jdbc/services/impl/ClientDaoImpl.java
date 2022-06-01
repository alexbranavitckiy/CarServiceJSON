package com.netcracker.jdbc.services.impl;

import com.netcracker.errors.PersistException;
import com.netcracker.jdbc.services.ClientDao;
import com.netcracker.jdbc.services.TemplateJDBCDao;
import com.netcracker.user.Client;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ClientDaoImpl extends TemplateJDBCDao<Client, UUID> implements ClientDao {

 @Override
 public String getSelectQuery() {
  return "SELECT * FROM public.clients;";
 }

 @Override
 public String getSelectByIdQuery() {
  return "SELECT * FROM clients where  id=?;";
 }

 @Override
 public String getSelectByPasswordAndLogin() {
  return "SELECT * FROM clients where  password=? AND login=?;";
 }

 @Override
 public String getCreateQuery() {
  return "INSERT INTO clients(id, password, login, first_name, role_user, last_name, email, phone, descriptions)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
 }

 @Override
 public String getUpdateQuery() {
  return "UPDATE clients SET id=?, password=?, login=?, first_name=?, role_user=?, last_name=?, email=?, phone=?, descriptions=? WHERE clients.id=?;";
 }

 @Override
 public String getDeleteQuery() {
  return "DELETE FROM public.clients WHERE clients.id= ?;";
 }

 @Override
 protected List<Client> parseResultSet(ResultSet rs) throws PersistException {
  List<Client> clients = new ArrayList<>();
  try {
   while (rs.next()) {
    Client client = Client.builder()
     .id(UUID.fromString(rs.getString("id")))
     .description(rs.getString("descriptions"))
     .phone(rs.getString("phone"))
     .login(rs.getString("login"))
     .password(rs.getString("password"))
     .name(rs.getString("first_name") + rs.getString("last_name"))
     .email(rs.getString("email"))
     .carClients(new ArrayList<>())
     .roleUser((UUID) rs.getObject("role_user"))
     .build();
    clients.add(client);
   }
   return clients;
  } catch (Exception e) {
   throw new PersistException(e);
  }

 }

 @Override
 protected void prepareStatementForInsert(PreparedStatement preparedStatement, Client client)
  throws PersistException {
  try {
   addQuery(preparedStatement, client);
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 private void addQuery(PreparedStatement preparedStatement, Client client) throws SQLException {
  preparedStatement.setObject(1, client.getId());
  preparedStatement.setString(2, client.getPassword());
  preparedStatement.setString(3, client.getLogin());
  preparedStatement.setString(4, client.getName());
  preparedStatement.setObject(5, client.getRoleUser());
  preparedStatement.setString(6, client.getName());
  preparedStatement.setString(7, client.getEmail());
  preparedStatement.setObject(8, client.getPhone());
  preparedStatement.setString(9, client.getDescription());
 }


 @Override
 protected void prepareStatementForUpdate(PreparedStatement statement, Client client)
  throws PersistException {
  try {
   addQuery(statement, client);
   statement.setObject(10, client.getId());
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }
}