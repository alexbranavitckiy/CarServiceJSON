package com.netcracker;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.user.Master;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MasterServices {

 List<Master> getAllMaster() throws EmptySearchException;

 boolean addMaster(Master master);

 Optional<Master> getMasterById(UUID master) throws EmptySearchException;

 boolean updateMaster(Master master);
}
