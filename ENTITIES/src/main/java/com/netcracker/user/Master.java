package com.netcracker.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netcracker.EntityId;
import lombok.*;

import java.util.List;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Master extends Employers implements EntityId<UUID>, Cloneable {

 private List<UUID> outfits;

 @Builder
 public Master(UUID id, String name, String phone, String mail, String description,
               Role role, String login, String password, String homeAddress,
               Qualification qualificationEnum, String education, List<UUID> outfits) {
  super(id, name, phone, mail, description, role, login, password, homeAddress, qualificationEnum,
   education);
  this.outfits = outfits;
 }

 @Override
 public Master clone() throws CloneNotSupportedException {
  return (Master) super.clone();
 }

 @Override
 public String toString() {
  return "Master{" +
   "outfits=" + outfits +
   "} " + super.toString();
 }
}
