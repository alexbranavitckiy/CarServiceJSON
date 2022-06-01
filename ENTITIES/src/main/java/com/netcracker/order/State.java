package com.netcracker.order;

import com.netcracker.EntityId;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public enum State implements EntityId<UUID> {
  TEMPLATE,
  RECORDED,
  IN_WORK,
  CAR_GIVEN,
  CAR_ACCEPTED,
  WAIT_CLIENT,
  REQUEST,
  BID;

  @Override
  public UUID getId() {
    return UUID.nameUUIDFromBytes(this.name().getBytes(
      StandardCharsets.UTF_8));
  }
}
