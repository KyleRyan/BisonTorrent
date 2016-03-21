package Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author philippy
 */
public class ClientFileListener extends Thread {

    public ClientFileListener() {
    }
    ServerSocket serverSocket = null;

    public void run() {
        try {
            serverSocket = new ServerSocket(10005);
            System.out.println("Started on: ");
        } catch (IOException e) {
        }

        while (true) {
            try {
                new ClientFileSend(serverSocket.accept()).start();
            } catch (IOException ex) {
            }
        }
    }
}
