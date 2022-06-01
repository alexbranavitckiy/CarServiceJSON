package com.netcracker.user;

import com.netcracker.EntityId;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public enum RoleUser implements EntityId<UUID> {
 REGISTERED,
 UNREGISTERED;

 @Override
 public UUID getId() {
  return UUID.nameUUIDFromBytes(this.name().getBytes(
   StandardCharsets.UTF_8));
 }

}
