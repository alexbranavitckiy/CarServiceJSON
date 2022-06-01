package com.netcracker.time;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.netcracker.EntityId;
import com.netcracker.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Label implements EntityId<UUID> {

 private UUID id;

 private Date date;

 private String description;

 private Order order;
}
