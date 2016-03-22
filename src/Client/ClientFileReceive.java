package Client;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientFileReceive extends Thread {

    private Socket socket = null;
    private String remote = "";
    private String fileName = "";

    public ClientFileReceive(String fileName, String ip) {
        super("Receive");
        remote = ip;
        this.fileName = fileName;
    }

    public void run() {

        try {
            socket = new Socket(InetAddress.getByName(remote), 10005);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeUTF(fileName);
            out.flush();
            
            Files.copy(socket.getInputStream(), Paths.get("./shared/a" + fileName));
            socket.close();
        } catch (Exception e) {
        }
    }
}
