package demotcp;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCP {
    private final int DEFAULT_SERVER_PORT = 16789;
    private final String hostname = "localhost";
    Socket socket;

    public ClientTCP() throws UnknownHostException, IOException {
        socket = new Socket(InetAddress.getByName(hostname), DEFAULT_SERVER_PORT);
    }
    
    public void closeTCP() {
        try {
            if (!socket.isClosed())
                socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connectTCP() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            int rd;
            out.write("Date".getBytes());
            byte[] date = new byte[1024];
            rd = in.read(date);
            if (rd > 0)
                System.out.println("Current date: " + new String(date));
            out.write("Time".getBytes());
            byte[] time = new byte[1024];
            rd = in.read(time);
            if (rd > 0)
                System.out.println("Current time: " + new String(time));
            out.write("Disconnect".getBytes());
            socket.shutdownInput();
            socket.shutdownOutput();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP();
            client.connectTCP();
            client.closeTCP();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
