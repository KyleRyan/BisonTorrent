package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import Client.Request;

public class ServerThread extends Thread {

	public Socket socket = null;
	public BufferedReader in = null;
	public BufferedWriter out = null;
	public InetAddress address = null;
	public HashSet<String> ownedFiles = new HashSet<String>();

	public ServerThread(Socket socket) throws IOException {
		super("ServerThread");
		this.socket = socket;
		this.address = socket.getInetAddress();
	}

	public Serializable find(Serializable requestBody) {
		try {
			return Server.find((String) requestBody);
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			return new LinkedList<InetAddress>();
		}
	}

	public void updateFiles(Serializable requestBody) {
		try {
			// determine files to add or remove

			LinkedList<String> tempVar = new LinkedList<String>(
					(Collection<? extends String>) requestBody);
			System.out.println("tempVar: " + tempVar);
			HashSet<String> currentOwnedFiles = new HashSet<String>();
			for(String var : tempVar){
				System.out.println(var);
				currentOwnedFiles.add(var);
			}
			
			System.out.println(currentOwnedFiles.toString());
			
			HashSet<String> filesToRemove = new HashSet<String>(ownedFiles);
			filesToRemove.removeAll(currentOwnedFiles);
			Server.removeFiles(address, filesToRemove);

			HashSet<String> filesToAdd = new HashSet<String>(currentOwnedFiles);
			filesToAdd.removeAll(ownedFiles);
			Server.addFiles(address, filesToAdd);

			// Server.addFiles(address, currentOwnedFiles);
			// for(Iterator<String> iterator = currentOwnedFiles.iterator();
			// iterator.hasNext();)
			// Server.addFiles(address, files);
			// Server.removeFiles(address, files);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			while (true) {
				Request request = (Request) ois.readObject();
				System.out.println(request.getRequestBody());
				//if (ois.available() > 0) {
					// Request request = (Request) ois.readObject();
					if (request != null) {
						System.out.println("Request Type: " + request.getRequestType());
						switch (request.getRequestType()) {
						case 0:
							updateFiles(request.getRequestBody());
							// Server.addFiles(address,
							// (LinkedList<String>) request.getRequestBody());
							break;
						case 1:
							Request response = new Request(2,
									find(request.getRequestBody()));
							System.out.println("Responds is " + response.getRequestBody());
							oos.writeObject(response);
							break;
						}
					}
				//} else {
				Thread.sleep(1000);
				//}
			}
		} catch (Exception e) {
			// System.out.println(e);
			e.printStackTrace();
		}
	}
}
