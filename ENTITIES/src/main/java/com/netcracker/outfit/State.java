package com.netcracker.outfit;


import com.netcracker.EntityId;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public enum State implements EntityId<UUID> {
 WORK,
 END,
 RECORDED,
 NO_STATE;

 @Override
 public UUID getId() {
  return UUID.nameUUIDFromBytes(this.name().getBytes(
   StandardCharsets.UTF_8));
 }
}
