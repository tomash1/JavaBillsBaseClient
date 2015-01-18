
package pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionController;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionModel.ServerClient;
import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionView.AppLogger;
import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionView.PleaseWaitInfo;
/**
 * Class designed to become a model in MVC architecture
 * Manages database in application
 * 
 * @author tomaszdomaracki
 * @version 1.0.0 
 */

public class ServerConnectionController extends SwingWorker<Void, Void>{
    
    private ServerClient model;
    private AppLogger logger;
    private BufferedOutputStream serverStream;
    private BufferedReader serverAnswerStream;
    private String defaultDbPath;
    private List<String> photosPaths;
    private PleaseWaitInfo infoFrame;
            
    public ServerConnectionController(String defaultDbPath, List<String> photosPaths, PleaseWaitInfo infoFrame) throws IOException{
        logger = AppLogger.getInstance();
        try{
            this.model = new ServerClient();
            
            serverStream       = model.getServerDataSendStream();
            serverAnswerStream = model.getServerStream();
            this.defaultDbPath  = defaultDbPath;
            this.photosPaths = photosPaths;
            this.infoFrame = infoFrame;
            writeToLogMessageFromServer();
        }
        catch(IOException ex){
            showServerNotFoundDialog();
            throw new IOException("Server not found.");
        }
    }
    
    @Override
    protected Void doInBackground() throws Exception {
            sendDatabaseFileToServer();
            sendAllPhotoFilesToServer();
            sendEndOfTransmissionCode();
            return null;
    }

    @Override
    protected void done() {
        infoFrame.setVisible(false);
        super.done();
    }
    
    public BufferedReader getServerStream(){
        return model.getServerStream();
    }
    
    public void sendEndOfTransmissionCode(){       
        try {
            serverStream.write("EOT".getBytes(), 0, (int)"EOT".length());
            serverStream.flush();
            writeToLogMessageFromServer();
            showTransmissionSuccessfulDialog();
        } catch (IOException ex) {
            showTransmissionErrorDialog();
            logger.addTextToLog("Can not comunicate with server. Check internet connection.");
        }
    }
    
    public void sendAllPhotoFilesToServer(){
        for(String photoPath : photosPaths){
            File photoFile = new File(photoPath);
            try {   
                byte[] dbFileByteArray = Files.readAllBytes(Paths.get(photoFile.getPath()));
                serverStream.write("IMAGE".getBytes(), 0, (int)"IMAGE".length());
                serverStream.flush();
                writeToLogMessageFromServer();
                serverStream.write(photoFile.getName().getBytes(), 0, (int)photoFile.getName().length());
                serverStream.flush();
                writeToLogMessageFromServer();
                serverStream.write(dbFileByteArray, 0, (int)photoFile.length());
                serverStream.flush();
                writeToLogMessageFromServer();
            } catch (IOException ex) {
                showTransmissionErrorDialog();
                logger.addTextToLog("Can not comunicate with server. Check internet connection.");
            }
            
        }
    }
    
    public void sendDatabaseFileToServer(){
        File dbFile = new File(defaultDbPath);
        try {
            byte[] dbFileByteArray = Files.readAllBytes(Paths.get(dbFile.getPath()));
            serverStream.write("DATABASE".getBytes(), 0, (int)"DATABASE".length());
            serverStream.flush();
            writeToLogMessageFromServer();
            serverStream.write(dbFileByteArray, 0, (int)dbFile.length());
            serverStream.flush();
            writeToLogMessageFromServer();
            
        } catch (IOException ex) {
            showTransmissionErrorDialog();
            logger.addTextToLog("Can not comunicate with server. Check internet connection.");
        }
    }
    
    private void showServerNotFoundDialog(){
        String message = "Can not find server. More info in logger.";
        JOptionPane infoBox = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog infoDialog = infoBox.createDialog(infoBox, message);
        infoDialog.setTitle("Can not find server");
        infoDialog.setVisible(true);
        String messageToLog = "Check your internet connection, or try again later.";
        logger.addTextToLog(messageToLog);
    }
    
    private void showTransmissionSuccessfulDialog(){
        String message = "Database saved on server.";
        JOptionPane infoBox = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog infoDialog = infoBox.createDialog(infoBox, message);
        infoDialog.setTitle("Success !");
        infoDialog.setVisible(true);
        String messageToLog = "Success.";
        logger.addTextToLog(messageToLog);
    }
    
    private void showTransmissionErrorDialog(){
        String message = "Error when sending files to server occured.";
        JOptionPane errorBox = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog infoDialog = errorBox.createDialog(errorBox, message);
        infoDialog.setTitle("Error");
        infoDialog.setVisible(true);
    }
    
    private void writeToLogMessageFromServer() throws IOException{
        boolean receivedMessage = false;
        String message ="";
        while(!receivedMessage){
            message = serverAnswerStream.readLine();
            if (!message.equals("")){
                receivedMessage = true;
            }
        }
        logger.addTextToLog(message);
    }   
}
