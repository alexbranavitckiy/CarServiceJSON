package com.netcracker.factory;

import com.netcracker.*;

public interface DaoFactory {

 ClientServices getClientServices();

 CarServices getCarServices();

 MasterServices getMasterServices();

 LoginServices getLoginServices();

 OutfitsServices getOutfitServices();

 OrderServices getOrderServices();

 MasterReceiverServices getMasterReceiverServices();

 CarBreakdownServices getCarBreakdownServices();

}
