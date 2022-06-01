package com.netcracker.menu;

import com.netcracker.errors.EmptySearchException;

import java.io.IOException;
import java.util.Scanner;

public interface Menu {

 void run(Scanner in, String parentsName) throws IOException, EmptySearchException;

 void preMessage(String parentsName);

}
