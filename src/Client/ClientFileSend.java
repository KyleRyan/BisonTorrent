package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientFileSend extends Thread {

    private Socket socket = null;

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
