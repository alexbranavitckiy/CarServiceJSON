package com.netcracker.outfit;

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
public class Outfit implements EntityId<UUID> {

    private UUID id;

    private String name;

    private String description;

    private UUID order;

    private UUID employer;

    private Date dateStart;

    private Date dateEnt;

    private double price;

    private UUID stateOutfit;

}
