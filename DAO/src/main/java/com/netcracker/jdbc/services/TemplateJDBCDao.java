package com.netcracker.jdbc.services;

import com.netcracker.EntityId;
import com.netcracker.errors.PersistException;
import com.netcracker.jdbc.ConnectorDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.netcracker.marka.CarClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TemplateJDBCDao<T extends EntityId<PK>, PK extends UUID> implements
 CrudDao<T, PK> {

 public abstract String getSelectQuery();

 public abstract String getCreateQuery();

 public abstract String getUpdateQuery();

 public abstract String getDeleteQuery();

 public abstract String getSelectByIdQuery();

 protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

 protected abstract void prepareStatementForInsert(PreparedStatement statement, T object)
  throws PersistException;

 protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object)
  throws PersistException;

 public Optional<T> getById(PK uuid) throws PersistException {
  List<T> list;
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(getSelectByIdQuery())) {
   statement.setObject(1, uuid);
   ResultSet rs = statement.executeQuery();
   list = parseResultSet(rs);
  } catch (Exception e) {
   throw new PersistException(e);
  }
  return Optional.ofNullable(list.iterator().next());
 }

 public List<T> getAll() throws PersistException {
  List<T> list;
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(getSelectQuery())) {
   ResultSet rs = statement.executeQuery();
   list = parseResultSet(rs);
  } catch (Exception e) {
   throw new PersistException(e);
  }
  return list;
 }

 public boolean delete(T object) throws PersistException {
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(getDeleteQuery())) {
   statement.setObject(1, object.getId());
   statement.executeUpdate();
   return true;
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }


 public boolean addObject(T object) throws PersistException {
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(getCreateQuery())) {
   prepareStatementForInsert(statement, object);
   statement.executeUpdate();
   return true;
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 public boolean update(T object) throws PersistException {
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(getUpdateQuery())) {
   prepareStatementForUpdate(statement,
    object);
   statement.executeUpdate();
   return true;
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 public boolean addByQuery(T object, String query) throws PersistException {
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(query)) {
   prepareStatementForInsert(statement, object);
   statement.executeUpdate();
   return true;
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }

 public List<T> getAllByAnyQuery(String query, Object... objects) throws PersistException {
  List<T> list;
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(query)) {
   this.statementOne(statement, objects);
   ResultSet rs = statement.executeQuery();
   list = parseResultSet(rs);
  } catch (Exception e) {
   throw new PersistException(e);
  }
  return list;
 }

 public List<T> getAllByQuery(Object object, String query) throws PersistException {
  List<T> list;
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(query)) {
   statement.setObject(1, object);
   ResultSet rs = statement.executeQuery();
   list = parseResultSet(rs);
  } catch (Exception e) {
   throw new PersistException(e);
  }
  return list;
 }

 public void statementOne(PreparedStatement statement, Object... objects) throws SQLException {
  for (int x = 0; x < objects.length; x++) {
   statement.setObject(x + 1, objects[x]);
  }
 }

 public boolean byAnyQuery(String query, Object... objects) throws PersistException {
  try (Connection connection = ConnectorDB.getConnection();
       PreparedStatement statement = connection.prepareStatement(query)) {
   this.statementOne(statement, objects);
   statement.executeUpdate();
   return true;
  } catch (Exception e) {
   throw new PersistException(e);
  }
 }
}