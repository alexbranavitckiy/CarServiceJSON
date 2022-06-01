package com.netcracker.file.services;

import com.netcracker.EntityId;
import com.netcracker.errors.EmptySearchException;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface CRUDServices<T extends EntityId<PK>, PK extends UUID> {

 List<T> getAll(File file, Class<T[]> object) throws EmptySearchException;

 boolean addObject(T addObject, File file, Class<T[]> typeObject);

 boolean deleteObjectById(T deleteObject, File file, Class<T[]> typeObject);

 boolean updateObject(T object, File file, Class<T[]> typeObject);

}
