package com.netcracker.file;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperServices {

 private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
 private final ObjectMapper objectMapperWrite = new ObjectMapper();

 public static ObjectMapper getObjectMapper() {//return via method to configure
  OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  return OBJECT_MAPPER;
 }

 public ObjectMapper getObjectMapperWrite() {
  return objectMapperWrite;
 }

}
