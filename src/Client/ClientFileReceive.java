package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
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
        DataOutputStream out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(InetAddress.getByName(remote), 10005);
            out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes(fileName);
            
            socket.getInputStream();
            
            out = new DataOutputStream(new FileOutputStream("./shared/"+fileName));
            byte by[] = new byte[BUFFER_SIZE];
//            int index = is.read(by, 0, BUFFER_SIZE);
//            while (index != -1) {
//                out.write(by, 0, index);
//                index = is.read(by, 0, BUFFER_SIZE);
//            }
            out.flush();
            out.close();

            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientFileSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
