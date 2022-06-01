package com.netcracker;


import java.io.IOException;


public interface LoginServices {

 boolean searchByUserLoginAndPassword(String login, String password) throws IOException;

}
