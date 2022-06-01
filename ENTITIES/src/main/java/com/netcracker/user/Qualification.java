package com.netcracker.user;


import com.netcracker.EntityId;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public enum Qualification implements EntityId<UUID> {
 ELECTRICIAN,
 DISC_EDITING;

 @Override
 public UUID getId() {
  return UUID.nameUUIDFromBytes(this.name().getBytes(
   StandardCharsets.UTF_8));
 }

}
