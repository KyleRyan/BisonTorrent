package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import Client.Request;

public class ServerThread extends Thread {

	public Socket socket = null;
	public BufferedReader in = null;
	public BufferedWriter out = null;

	public ServerThread(Socket socket) throws IOException {
		super("ServerThread");
		this.socket = socket;
	}

	public void run() {

		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			while (true) {
				Request request = (Request) ois.readObject();
				if (request != null) {
					switch (request.getRequestType()) {
					case 0:

						break;
					case 1:

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
