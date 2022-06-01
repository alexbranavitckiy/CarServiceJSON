package com.netcracker.user;


import com.netcracker.EntityId;
import lombok.*;


import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MasterReceiver extends Employers implements EntityId<UUID>, Cloneable {

 private List<UUID> orders;

 @Builder
 public MasterReceiver(UUID id, String name, String phone, String mail, String description,
                       Role role, String login, String password, String homeAddress, Qualification qualificationEnum,
                       String education, List<UUID> orders) {
  super(id, name, phone, mail, description, role, login, password, homeAddress, qualificationEnum,
   education);
  this.orders = orders;
 }

 @Override
 public String toString() {
  return "MasterReceiver{" +
   "orders=" + orders +
   "} " + super.toString();
 }

 @Override
 public MasterReceiver clone() throws CloneNotSupportedException {
  return (MasterReceiver)super.clone();
 }

}

