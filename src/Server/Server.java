package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Server {

	public static HashMap<InetAddress, Long> clientList = new HashMap();
	private static HashMap<String, HashSet<InetAddress>> fileMappings = new HashMap<String, HashSet<InetAddress>>();

	public static void main(String[] args) throws IOException {

		try (ServerSocket listener = new ServerSocket(10000);) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						listener.close();
						System.out.println("The server is shut down!");
					} catch (IOException e) { /* failed */
					}
				}
			});
			while (true) {
				System.out.println("doing stuff");
				Socket connection = listener.accept();
				ServerThread thread = new ServerThread(connection);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

	public synchronized static void addClient(InetAddress i, Long l) {

		if (clientList.put(i, l) == null) {
			System.out.println(i.getHostAddress() + " Has Connected.");
		}
	}

	public synchronized static void deleteClient(InetAddress i) {
		System.out.println(i.getHostAddress() + " Timed Out.");
		clientList.remove(i);
	}

	public synchronized static LinkedList<InetAddress> find(String fileName) {
		LinkedList<InetAddress> clientsWithFile = new LinkedList<InetAddress>(
				fileMappings.get(fileName));
		return clientsWithFile;
	}

	public synchronized static void addFiles(InetAddress client,
			HashSet<String> files) {
		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			String file = iterator.next();
			HashSet<InetAddress> clientsWithFile = fileMappings.get(file);
			if(clientsWithFile == null)
				clientsWithFile = new HashSet<InetAddress>();
			System.out.println("Client " + client + "File " + file + "clients with file" + clientsWithFile);
			clientsWithFile.add(client);
			fileMappings.put(file, clientsWithFile);
		}
		System.out.println("fileMappings: " + fileMappings.toString());
	}

	public synchronized static void removeFiles(InetAddress client,
			HashSet<String> files) {
		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			String file = iterator.next();
			HashSet<InetAddress> clientsWithFile = fileMappings.get(file);
			clientsWithFile.remove(client);
			fileMappings.put(file, clientsWithFile);
		}
	}
}
