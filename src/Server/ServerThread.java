package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {

	public Socket socket = null;
	public BufferedReader in = null;
	public BufferedWriter out = null;

	public ServerThread(Socket socket) throws IOException {
		super("ServerThread");
		this.socket = socket;
	}

	public void run() {

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
