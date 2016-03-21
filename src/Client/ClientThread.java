package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

public class ClientThread extends Thread {

    private Socket socket = null;
    private static final int BUFFER_SIZE = 32768;

    public ClientThread(String name) throws IOException {
        super(name);
        socket = new Socket();
    }

    public void run() {
        DataOutputStream out = null;
        BufferedReader in = null;

        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            int cnt = 0;
            String urlToCall = "";
            ///////////////////////////////////
            //begin get request from client
            while ((inputLine = in.readLine()) != null) {
                try {
                    StringTokenizer tok = new StringTokenizer(inputLine);
                    tok.nextToken();
                } catch (Exception e) {
                    break;
                }
                //parse the first line of the request to find the url
                if (cnt == 0) {
                    String[] tokens = inputLine.split(" ");
                    urlToCall = tokens[1];
                    if (!urlToCall.contains("http://")) {
                        urlToCall = "http://" + urlToCall;
                    }
                }
                cnt++;
            }
            //end get request from client
            ///////////////////////////////////
        } catch (Exception e) {
        }

        try {
            ///////////////////////////////////
            //begin send request to server, get response from server
            URL url = new URL("");
            URLConnection conn = url.openConnection();
            // Get the response
            InputStream is = null;
            /*if (cache.cacheCheck(urlToCall)) {
                    is = cache.use(urlToCall);
                } else {*/
            try {
                is = conn.getInputStream();
            } catch (IOException ioe) {
                System.out.println(
                        "********* IO EXCEPTION **********: " + ioe);
            }
            //}
            //end send request to server, get response from server
            ///////////////////////////////////

            ///////////////////////////////////
            //begin send response to client
            /*if (!cache.cacheCheck(urlToCall)) {
                    cache.create(urlToCall, is);
                    is = cache.use(urlToCall);
                }*/
            byte by[] = new byte[BUFFER_SIZE];
            int index = is.read(by, 0, BUFFER_SIZE);
            while (index != -1) {
                out.write(by, 0, index);
            }
            index = is.read(by, 0, BUFFER_SIZE);
            out.flush();
        } catch (Exception e) {
        }
        //end send response to client
        ///////////////////////////////////
        try {
            socket.close();
        } catch (IOException ex) {
        }
    }
}
