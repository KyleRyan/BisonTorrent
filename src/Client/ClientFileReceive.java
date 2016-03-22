package Client;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientFileReceive extends Thread {

    private Socket socket = null;
    private InetAddress remote = null;
    private String fileName = "";

    public ClientFileReceive(String fileName, InetAddress ip) {
        super("Receive");
        remote = ip;
        this.fileName = fileName;
    }

    public void run() {

        try {
            socket = new Socket(remote, 10005);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeUTF(fileName);
            out.flush();
            
            Files.copy(socket.getInputStream(), Paths.get("./shared/" + fileName));
            socket.close();
        } catch (Exception e) {
        }
    }
}
