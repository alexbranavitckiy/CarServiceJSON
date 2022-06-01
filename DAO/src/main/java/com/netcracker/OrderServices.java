package com.netcracker;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderServices {

 boolean addOrder(Order order);

 boolean repairRequest(Order order);

 List<Order> getAll() throws EmptySearchException;

 boolean updateOrder(Order order);

 List<Order> getOrderWithRequestState();

 boolean cancelRequest(UUID uuidCar);

 Optional<Order> getOrderByIdCar(UUID car);

 Optional<Order> getOrderById(UUID uuid);
}
