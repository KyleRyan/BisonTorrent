package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Server {

	public static HashMap<InetAddress, Long> clientList = new HashMap();
	private static HashMap<String, HashSet<InetAddress>> fileMappings = new HashMap<String, HashSet<InetAddress>>();

	public static void main(String[] args) throws IOException {
		new ServerListen().start();
		while (true) {
			try {
				new ServerThread(new ServerSocket(10000).accept()).start();
			} catch (Exception e) {

			}
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
			LinkedList<String> files) {
		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			String file = iterator.next();
			HashSet<InetAddress> clientsWithFile = fileMappings.get(file);
			clientsWithFile.add(client);
			fileMappings.put(file, clientsWithFile);
		}
	}

	public synchronized static void removeFiles(InetAddress client,
			LinkedList<String> files) {
		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			String file = iterator.next();
			HashSet<InetAddress> clientsWithFile = fileMappings.get(file);
			clientsWithFile.remove(client);
			fileMappings.put(file, clientsWithFile);
		}
	}
}
