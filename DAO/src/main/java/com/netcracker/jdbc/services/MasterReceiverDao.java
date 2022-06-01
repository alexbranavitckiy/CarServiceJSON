package com.netcracker.jdbc.services;

import com.netcracker.user.MasterReceiver;

import java.util.UUID;

public interface MasterReceiverDao extends CrudDao<MasterReceiver, UUID> {
 String getSelectByPasswordAndLogin();
}
