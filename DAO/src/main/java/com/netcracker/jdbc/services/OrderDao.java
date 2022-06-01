package com.netcracker.jdbc.services;


import com.netcracker.order.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao extends CrudDao<Order, UUID> {

 String getAllOrderByState();

 String getRequestQueryByRepairRequest();

 String deleteOrderByIdCar();

 String getSelectByIdCarQuery();


}
