package Client;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {
        long startTime = -60000000000l;
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        byte[] buf = null;
        String[] availableClients = null;

        while (true) {
            try {

                if (args.length != 1) {
                    System.out.println("Usage: executable <hostname>");
                    return;
                }

                // get a datagram socket
                socket = new DatagramSocket();

                // send request
                if ((System.nanoTime() - startTime) / 1e9 > 6) {
                    startTime = System.nanoTime();
                    buf = "HELLO".getBytes();
                    InetAddress address = InetAddress.getByName(args[0]);
                    packet = new DatagramPacket(buf, buf.length, address, 9999);
                    socket.send(packet);
                }
                socket.close();
            } catch (Exception e) {
            }
//            try {
//                // get file list
//                buf = new byte[256];
//                packet = new DatagramPacket(buf, buf.length);
//                socket = new DatagramSocket(10001);
//                socket.setSoTimeout(1000);
//                socket.receive(packet);
//                // display response
//                String received = new String(packet.getData(), 0, packet.getLength());
//                received = received.replace("None/", "");
//                availableClients = received.split("/");
//                System.out.println(availableClients[0]);
//                socket.close();
//            } catch (Exception e) {
//            }

            try {
                new ClientThread("ClientServer").start();
            } catch (Exception e) {
            }
        }
    }
}
