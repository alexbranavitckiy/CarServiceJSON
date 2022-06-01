package com.netcracker.jdbc.services;

import com.netcracker.marka.CarClient;

import java.util.UUID;

public interface CarDao extends CrudDao<CarClient, UUID> {

 String getSelectAllByIdClient();

 String getAllCarClientWaitState();

 String getAllCarWithState();

}
