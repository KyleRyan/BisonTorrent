package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientFileReceive extends Thread {

    private Socket socket = null;
    private String remote = "";
    private String fileName = "";
    private static final int BUFFER_SIZE = 32768;

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
