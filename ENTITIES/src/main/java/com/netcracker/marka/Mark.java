package com.netcracker.marka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netcracker.EntityId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mark implements EntityId<UUID> {

 private UUID id;

 private String generation;

 private Date year;

 private double engineSize;

}
