package com.netcracker.jdbc.services;

import com.netcracker.user.Master;

import java.util.UUID;

public interface MasterDao extends CrudDao<Master, UUID> {

 String getSelectByPasswordAndLogin();
}
