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
	
	public LinkedList<InetAddress> searchDirectory(String file) {
		LinkedList<InetAddress> peerList = null;
		
        try {
    		Socket socket = new Socket("localhost", 9999);
    		OutputStream out = socket.getOutputStream();
        	ObjectOutputStream objout = new ObjectOutputStream(out);
        	Request req = new Request(0, file);
        	objout.writeObject(req);
        	objout.close();
        	out.close();
        	socket.close();
        	
        	ServerSocket serverResponds = new ServerSocket(9998);
        	Socket s = serverResponds.accept();
        	InputStream in = s.getInputStream();
        	ObjectInputStream objin = new ObjectInputStream(in);
        	Request r = (Request)objin.readObject();
        	if(r.getRequestType() == 2) {
        		peerList = (LinkedList<InetAddress>) r.getRequestBody();
        	}
        	in.close();
        	s.close();
        	serverResponds.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        return peerList;
	}
}