package pl.polsl.java.lab4.tomasz.domaracki.JavaBillsCollectionModel;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Class responsible for connecting to server
 *
 * @author tomaszdomaracki
 * @version 1.0.0
 */
    
public class ServerClient {
    
    /**
     * Representing server ip address
     */
    private final String serverIpAddress = "127.0.0.1";
    /**
     * Representing port on server
     */
    private final int serverPort = 9991;
    /**
     * Representing socket on client side
     */
    private final Socket clientSocket;
    /**
     * Representing output to server buffer
     */
    private final BufferedOutputStream out;
    /**
     * Representing input from server buffer
     */
    private final BufferedReader in;
    
    /**
     * The initiating constructor of the class ServerClient
     * 
     * @throws java.io.IOException
     */
    public ServerClient() throws IOException{

        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(serverIpAddress, serverPort), 1000);
        out          = new BufferedOutputStream(clientSocket.getOutputStream());
        in           = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));        
    }    
    
    /**
     * Gets server input buffer
     * 
     * @return input stream
     */
    public BufferedReader getServerStream(){
        return in;
    }
    
    /**
     * Gets server output buffer
     * 
     * @return output stream
     */
    public BufferedOutputStream getServerDataSendStream(){
        return out;
    }
}
