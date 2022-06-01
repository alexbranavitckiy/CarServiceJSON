package com.netcracker.user;

import com.netcracker.EntityId;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public enum Role implements EntityId<UUID> {

 MASTER,
 RECEPTIONIST;

 @Override
 public UUID getId() {
  return UUID.nameUUIDFromBytes(this.name().getBytes(
   StandardCharsets.UTF_8));
 }
}
