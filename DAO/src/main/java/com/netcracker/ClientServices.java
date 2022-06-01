package com.netcracker;

import com.netcracker.errors.EmptySearchException;
import com.netcracker.user.Client;

import java.io.IOException;
import java.util.List;

public interface ClientServices {

 List<Client> getAllClient() throws EmptySearchException;

 boolean addObjectInClient(Client client) throws IOException;

 boolean addObjectInClientNotOnline(Client client) throws IOException;

 boolean updateClient(Client client) throws IOException;

 boolean updateClientNotSession(Client client) throws IOException;

}
