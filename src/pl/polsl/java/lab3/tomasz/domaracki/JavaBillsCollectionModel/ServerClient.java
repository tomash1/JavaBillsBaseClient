package pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionModel;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Class representing temporary database created for testing
 *
 * @author toamszdomaracki
 * @version 1.0.0
 */

public class ServerClient {
    
    /**
     * 
     */
    private final String serverIpAddress = "127.0.0.1";
    /**
     * 
     */
    private final int serverPort = 9991;
    /**
     * 
     */
    private final Socket clientSocket;
    /**
     * 
     */
    private final BufferedOutputStream out;
    /**
     * 
     */
    private final BufferedReader in;
    
    /**
     * 
     * @throws java.io.IOException
     */
    public ServerClient() throws IOException{

        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(serverIpAddress, serverPort), 1000);
        out          = new BufferedOutputStream(clientSocket.getOutputStream());
        in           = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));        
    }    
    
    public BufferedReader getServerStream(){
        return in;
    }
    
    public BufferedOutputStream getServerDataSendStream(){
        return out;
    }
}
