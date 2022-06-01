package com.netcracker.jdbc.services;

import com.netcracker.user.Client;

import java.util.UUID;

public interface ClientDao extends CrudDao<Client, UUID> {
 String getSelectByPasswordAndLogin();
}
