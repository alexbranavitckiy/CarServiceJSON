package com.netcracker;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.user.MasterReceiver;

import java.io.IOException;
import java.util.List;

public interface MasterReceiverServices {

 boolean updateMaster(MasterReceiver masterReceiver) throws IOException;

 boolean updateMasterAndSession(MasterReceiver masterReceiver) throws IOException;

 boolean addMaster(MasterReceiver masterReceiver);

 List<MasterReceiver> getAllMasterReceiver() throws EmptySearchException;
}
