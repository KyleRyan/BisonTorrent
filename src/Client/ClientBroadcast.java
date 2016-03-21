package Client;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientBroadcast extends Thread {

    long startTime = -60000000000l;
    DatagramSocket socket = null;
    DatagramPacket packet = null;
    byte[] buf = null;
    String[] availableClients = null;
    InetAddress remoteServer = null;
    LinkedList<String> fileList = new LinkedList<String>();
    
    public ClientBroadcast(String i, String folder) {
        try {
            remoteServer = InetAddress.getByName(i);
            populateFileList(new File(folder));
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientBroadcast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            try {
                // send request
                if ((System.nanoTime() - startTime) / 1e9 > 6) {
                	socket = new DatagramSocket();
                    startTime = System.nanoTime();
                    buf = "HELLO".getBytes();
                    packet = new DatagramPacket(buf, buf.length, remoteServer, 9999);
                    socket.send(packet);
                    socket.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    private void populateFileList(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            	populateFileList(fileEntry);
            } else {
            	fileList.add(fileEntry.getName());
            }
        }
    }
    
    public LinkedList<String> getFileList() {
    	return fileList;
    }
}
