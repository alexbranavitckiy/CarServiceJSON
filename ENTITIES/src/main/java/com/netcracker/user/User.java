package com.netcracker.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netcracker.EntityId;

import java.util.UUID;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class User implements EntityId<UUID> {

 private UUID id;

 private String name;

 private String phone;

 private String email;

 private String description;

 private String login;

 private String password;

 private UUID roleUser;



}
