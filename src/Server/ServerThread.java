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
import java.util.LinkedList;

import Client.Request;

public class ServerThread extends Thread {

	public Socket socket = null;
	public BufferedReader in = null;
	public BufferedWriter out = null;
	public InetAddress address = null;

	public ServerThread(Socket socket) throws IOException {
		super("ServerThread");
		this.socket = socket;
		this.address = InetAddress.getByName(socket.getRemoteSocketAddress()
				.toString());

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

			// HashSet<String>()
			// Server.addFiles(address, files);
			// Server.removeFiles(address, files);
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
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
				if (request != null) {
					switch (request.getRequestType()) {
					case 0:
						// Server.addFiles(address,
						// (LinkedList<String>) request.getRequestBody());
						break;
					case 1:
						Request response = new Request(2,
								find(request.getRequestBody()));
						oos.writeObject(response);
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// try {
		// String clients = "None";
		// for (Map.Entry<InetAddress, Long> entry :
		// Server.clientList.entrySet()) {
		// clients += entry.getKey();
		// }
		// byte[] buf = clients.getBytes();
		// // send the list of clients to all clients
		// for (Map.Entry<InetAddress, Long> entry :
		// Server.clientList.entrySet()) {
		// packet = new DatagramPacket(buf, buf.length, entry.getKey(), 10001);
		// DatagramSocket sendSocket = new DatagramSocket();
		// sendSocket.send(packet);
		// sendSocket.close();
		// }
		// } catch (Exception e) {
		// }
		// socket.close();

	}
}
