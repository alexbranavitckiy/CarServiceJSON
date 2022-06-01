package com.netcracker;

import com.netcracker.breakdown.CarBreakdown;
import com.netcracker.errors.EmptySearchException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarBreakdownServices {

 List<CarBreakdown> getAllBreakdown() throws EmptySearchException;

 boolean addBreakdown(CarBreakdown carBreakdown);

 Optional<CarBreakdown> getBreakdownById(UUID uuid) throws EmptySearchException;

 boolean updateBreakdown(CarBreakdown carBreakdown);

 List<CarBreakdown> getAllBreakdownByCar(UUID uuid) throws EmptySearchException;

}
