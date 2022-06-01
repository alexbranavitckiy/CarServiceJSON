package com.netcracker.jdbc.services;

import com.netcracker.breakdown.CarBreakdown;

import java.util.UUID;

public interface CarBreakdownDao extends CrudDao<CarBreakdown, UUID>{

 String getAllCarBreakdownsById();
}
