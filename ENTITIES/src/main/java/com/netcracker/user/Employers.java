package com.netcracker.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Employers {

 private UUID id;

 private String name;

 private String phone;

 private String mail;

 private String description;

 private Role role;

 private String login;

 private String password;

 private String homeAddress;

 private Qualification qualification;

 private String education;

}
