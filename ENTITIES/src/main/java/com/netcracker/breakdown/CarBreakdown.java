package com.netcracker.breakdown;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netcracker.EntityId;
import com.netcracker.marka.CarClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarBreakdown implements EntityId<UUID> {

 private UUID id;

 private UUID carClient;

 private String description;

 private String runCarSize;

 private UUID state;

 private String location;

}
