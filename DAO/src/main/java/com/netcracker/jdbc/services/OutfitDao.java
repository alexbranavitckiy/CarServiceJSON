package com.netcracker.jdbc.services;

import com.netcracker.outfit.Outfit;

import java.util.UUID;

public interface OutfitDao extends CrudDao<Outfit, UUID> {

 String getAllSortByDateDesc();

 String getAllBetweenDate();

 String getAllByMaster();

 String getAllByMasterAndState();

 String getAllByState();
}
