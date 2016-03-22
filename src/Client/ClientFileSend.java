package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientFileSend extends Thread {

    private Socket socket = null;
    private static final int BUFFER_SIZE = 32768;

    public ClientFileSend(Socket socket) {
        super("Send");
        this.socket = socket;
    }

    public void run() {
        DataOutputStream out = null;
        BufferedReader in = null;

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String fileName = "";
            System.out.println(in.readLine());
            System.out.println(fileName);
            
            out = new DataOutputStream(socket.getOutputStream());
            byte[] by = new byte[BUFFER_SIZE];
            File file = new File("./shared/"+fileName);
//            file.
//            for (int i = 0; i < by.length; i++) {
//                out.write(by, );
//            }
//            out.write(, BUFFER_SIZE, BUFFER_SIZE);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientFileSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
