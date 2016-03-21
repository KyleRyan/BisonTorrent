package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server {

    public static HashMap<InetAddress, Long> clientList = new HashMap();
    
    public static void main(String[] args) throws IOException {
        new ServerListen().start();
        while (true) {
            try {
                new ServerThread(new ServerSocket(10000).accept()).start();
            } catch (Exception e) {
            	
            }
        }

    }

    public synchronized static void addClient(InetAddress i, Long l) {

        if (clientList.put(i, l) == null) {
            System.out.println(i.getHostAddress() + " Has Connected.");
        }
    }

    public synchronized static void deleteClient(InetAddress i) {
        System.out.println(i.getHostAddress() + " Timed Out.");
        clientList.remove(i);
    }
}
