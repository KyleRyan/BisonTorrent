package assignment.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
//import java.net.ServerSocket;

public class Server {

    public static HashMap<InetAddress, Long> clientList = new HashMap();

    //public static Logger log = new Logger();
    //public static Cache cache = new Cache();
    public static void main(String[] args) throws IOException {
        new ServerListen().start();
//        ServerSocket serverSocket = null;
//        boolean listening = true;
//
//        int port = 10000;	//default
//        try {
//            port = Integer.parseInt(args[0]);
//        } catch (Exception e) {
//            //ignore me
//        }
//
//        try {
//            serverSocket = new ServerSocket(port);
//            System.out.println("Started on: " + port);
//        } catch (IOException e) {
//            System.err.println("Could not listen on port: " + args[0]);
//            System.exit(-1);
//        }
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
