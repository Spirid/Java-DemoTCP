package demotcp;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {
    private final int DEFAULT_SERVER_PORT = 16789;
    private final int DEFAULT_SERVER_CLIENTS_NUMBER = 124;
    ServerSocket servSocket;
    Socket socket;
    
    public ServerTCP() {
        try {
            servSocket = new ServerSocket (DEFAULT_SERVER_PORT,  DEFAULT_SERVER_CLIENTS_NUMBER);
            socket = servSocket.accept();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeTCP() {
        try {
            if (!socket.isClosed())
                socket.close();
            if (!servSocket.isClosed())
                servSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connectTCP() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            int rd;
            byte[] msg = new byte[1024];
            while ((rd = in.read(msg)) > 0)
            {
                String request = new String(msg, 0, rd);
                if (request.equals("Disconnect"))
                    break;
                switch (request) {
                    case "Date":
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        Date d = new Date();
                        out.write((df.format(d)).getBytes());
                        break;
                    case "Time":
                        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
                        Date t = new Date();
                        out.write((tf.format(t)).getBytes());
                        break;
                    default:
                        out.write("Unknown request".getBytes());
                        break;
                }
            }
            socket.shutdownInput();
            socket.shutdownOutput();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        ServerTCP server = new ServerTCP();
        server.connectTCP();
        server.closeTCP();
    }
}
