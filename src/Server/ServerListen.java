package Server;

import static Server.Server.clientList;
import static Server.Server.deleteClient;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

public class ServerListen extends Thread {

    DatagramSocket socket = null;
    byte[] buf = null;
    DatagramPacket packet = null;

    public ServerListen() {
        try {
            socket = new DatagramSocket(9999);
            buf = new byte[5];
            packet = new DatagramPacket(buf, buf.length);
        } catch (Exception e) {
        }
    }

    public void run() {
        while (true) {
            try {
                // receive request
                socket.setSoTimeout(1000);
                socket.receive(packet);
                Server.addClient(packet.getAddress(), System.nanoTime());
                System.out.println("a");
            } catch (Exception e) {
            }
            InetAddress key = null;
            boolean delete = false;
            for (Map.Entry<InetAddress, Long> entry
                    : clientList.entrySet()) {
                key = entry.getKey();
                Long value = entry.getValue();
                if ((System.nanoTime() - value) / 1e9 > 200) {
                    delete = true;
                    break;
                }
            }
            if (delete) {
                deleteClient(key);
            }
        }
    }
}
