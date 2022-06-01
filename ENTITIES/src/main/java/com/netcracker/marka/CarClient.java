package com.netcracker.marka;

import com.netcracker.EntityId;
import lombok.*;


import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class CarClient implements EntityId<UUID> {

 private UUID id;

 private UUID id_clients;

 private String summary;

 private String description;

 private String ear;

 private String metadataCar;

 private String run;

 private List<UUID> carBreakdowns;

 private Mark mark;

 @Override
 public String toString() {
  return "Your cars: summer='" + summary + '\'' +
   ", ear='" + ear + '\'' +
   ", metadataCar='" + metadataCar + '\'' +
   ", run='" + run;
 }

}
