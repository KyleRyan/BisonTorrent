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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.IOUtils;

public class ClientFileSend extends Thread {

    private Socket socket = null;
    private static final int BUFFER_SIZE = 32768;

    public ClientFileSend(Socket socket) {
        super("Send");
        this.socket = socket;
    }

    public void run() {

        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String fileName = ois.readUTF();
            
            Files.copy(Paths.get("./shared/" + fileName), socket.getOutputStream());
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientFileSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
