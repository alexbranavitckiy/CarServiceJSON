package com.netcracker.file.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.EntityId;
import com.netcracker.errors.EmptySearchException;
import com.netcracker.file.services.CRUDServices;
import com.netcracker.file.ObjectMapperServices;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CRUDServicesImpl<T extends EntityId<PK>, PK extends UUID> implements CRUDServices<T, PK> {

 private final ObjectMapperServices objectMapperServices = new ObjectMapperServices();

 @Override
 public List<T> getAll(File file, Class<T[]> object) throws EmptySearchException {
  try {
   return new ArrayList<>(List.of(ObjectMapperServices.getObjectMapper()
    .readValue(file, object)));
  } catch (IOException e) {
   log.error("Output error:{}", e.getMessage());
  }
  throw new EmptySearchException("No output available");
 }

 @Override
 public boolean addObject(T addObject, File file, Class<T[]> typeObject) {
  try {
   List<T> list = new ArrayList<>(Arrays.asList(ObjectMapperServices
    .getObjectMapper().readValue(file, typeObject)));
   list.add(addObject);
   objectMapperServices.getObjectMapperWrite().writeValue(file, list);
   return true;
  } catch (Exception e) {
   log.error("Error adding object:{}", e.getMessage());
  }
  return false;
 }

 @Override
 public boolean deleteObjectById(T deleteObject, File file, Class<T[]> typeObject) {
  try {
   ObjectMapper objectMapper = new ObjectMapper();
   List<T> clients = new ArrayList<>(Arrays.asList(ObjectMapperServices.getObjectMapper()
    .readValue(file, typeObject)));
   objectMapper.writeValue(file,
    clients.stream().filter(x -> !deleteObject.getId().equals(x.getId())
    )
     .collect(Collectors.toList()));
   return true;
  } catch (Exception e) {
   log.error("Object deletion error:{}", e.getMessage());
  }
  return false;
 }

 @Override
 public boolean updateObject(T object, File file, Class<T[]> typeObject) {
  if (this.deleteObjectById(object, file, typeObject) && this.addObject(object,
   file, typeObject)) {
   return true;
  }
  log.error("Error deleting and adding user!!!");
  return false;
 }
}
