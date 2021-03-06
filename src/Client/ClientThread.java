package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

public class ClientThread extends Thread {

    private Socket socket = null;
    private ObjectOutputStream objout = null;
    private ObjectInputStream objin = null;
    long startTime = -60000000000l;
    LinkedList<String> fileList;
    InetAddress net;
    
    public ClientThread(LinkedList<String> files, InetAddress net) {
        super("client-thread");
        fileList = files;
        this.net = net;
    }

    public void run() {
    	try {
    		socket = new Socket(net, 10000);
    		OutputStream out = socket.getOutputStream();
    		objout = new ObjectOutputStream(out);
    		InputStream in = socket.getInputStream();
    		objin = new ObjectInputStream(in);
    		
    		
    		while(true) {
	        	if ((System.nanoTime() - startTime) / 1e9 > 10) {
	        		startTime = System.nanoTime();
	        		System.out.println("Sending file list");
		        	Request req = new Request(0, fileList);
		        	objout.writeObject(req);
	        	}
    		}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    public Socket getSocket() {
    	return socket;
    }
    
    public ObjectOutputStream getOutputStream() {
    	return objout;
    }
    
    public ObjectInputStream getInputStream() {
    	return objin;
    }
}
