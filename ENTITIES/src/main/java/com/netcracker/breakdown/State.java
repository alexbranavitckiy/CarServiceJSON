package com.netcracker.breakdown;


import com.netcracker.EntityId;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public enum State implements EntityId<UUID> {
 CORRECTED,
 NOT_FIXED,
 IMPORTANT,
 NEEDS_CORRECTED;

 @Override
 public UUID getId() {
  return UUID.nameUUIDFromBytes(this.name().getBytes(
   StandardCharsets.UTF_8));
 }

}
