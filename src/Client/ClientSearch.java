package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ClientSearch {
	
	public LinkedList<InetAddress> searchDirectory(ObjectOutputStream objout, ObjectInputStream objin, String file) {
		LinkedList<InetAddress> peerList = null;
		
        try {
        	Request req = new Request(1, file);
        	objout.writeObject(req);
        	
        	Request r = (Request)objin.readObject();
        	if(r.getRequestType() == 2) {
        		peerList = (LinkedList<InetAddress>) r.getRequestBody();
        	}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        return peerList;
	}
}