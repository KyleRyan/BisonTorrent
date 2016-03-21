package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread {

    private Socket socket = null;
    long startTime = -60000000000l;
    private static final int BUFFER_SIZE = 32768;
    LinkedList<String> fileList;
    
    public ClientThread(LinkedList<String> files) {
        super("client-thread");
        fileList = files;
    }

    public void run() {
    	OutputStream out = null;
    	ObjectOutputStream objout = null;
    	
        try {
        	if ((System.nanoTime() - startTime) / 1e9 > 6) {
        		socket = new Socket("localhost", 9999);
	        	out = socket.getOutputStream();
	        	objout = new ObjectOutputStream(out);
	        	Request req = new Request(0, fileList);
	        	objout.writeObject(req);
	        	objout.close();
	        	out.close();
	        	socket.close();
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
