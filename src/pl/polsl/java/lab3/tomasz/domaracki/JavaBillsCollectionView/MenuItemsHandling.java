package pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.filechooser.*;

import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionModel.Bill;
import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionController.ServerConnectionController;
import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionController.DatabaseController;

/**
 * Class implements handling for elements in program menu
 * 
 * @author tomaszdomaracki
 * @version 1.0.0
 */
public class MenuItemsHandling {
    
    /**
     * Database controller
     */
    private DatabaseController db;
    
    /**
     * The initiating constructor of the class MenuItemsHandling
     * 
     * @param db created database controller in app
     */
    public MenuItemsHandling(DatabaseController db){
        this.db = db;
    }
    
    /**
     * Gets database controller to main application if changed in this class
     * 
     * @return database controller
     */
    public DatabaseController getDatabaseController(){
        return db;
    }
    
    /**
     * Shows File Chooser for opening database
     * 
     * @param parentLayout layout to center created window
     * @return selected database by user
     * @throws IOException 
     */
    public File showFileChooserForDbOpen(javax.swing.JSplitPane parentLayout) throws IOException
    {
        JFileChooser openFileDialog = new JFileChooser();
        FileNameExtensionFilter databaseFilter = new FileNameExtensionFilter("Bills Database files", "billdb");
        openFileDialog.setFileFilter(databaseFilter);
        int result = openFileDialog.showOpenDialog(parentLayout);
        if(result == JFileChooser.APPROVE_OPTION){
            return openFileDialog.getSelectedFile();
        }
        else{
            throw new IOException();
        }
    }
    
    /**
     * Shows File Chooser for opening image 
     * 
     * @param parentLayout layout to center created window
     * @return selected image by user
     * @throws IOException 
     */
    public File showFileChooserForImageOpen(javax.swing.JSplitPane parentLayout) throws IOException
    {
        JFileChooser openFileDialog = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Supported image files", "jpg", "jpeg", "png");
        openFileDialog.setFileFilter(imageFilter);
        int result = openFileDialog.showOpenDialog(parentLayout);
        if(result == JFileChooser.APPROVE_OPTION){
            return openFileDialog.getSelectedFile();
        }
        else if(result == JFileChooser.CANCEL_OPTION){
            return null;
        }
        else{
            throw new IOException();
        }
    }
    
    /**
     *  Shows File Chooser for saving database 
     * 
     * @param parentLayout layout to center created window
     * @return saved database file
     * @throws IOException 
     */
    public File showFileChooserForDbSave(javax.swing.JSplitPane parentLayout) throws IOException
    {
        JFileChooser saveFileDialog = new JFileChooser();
        FileNameExtensionFilter dbFilesFilter = new FileNameExtensionFilter("Bills Database files", "billdb");
        saveFileDialog.setFileFilter(dbFilesFilter);

        int result = saveFileDialog.showSaveDialog(parentLayout);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = saveFileDialog.getSelectedFile();
            String fname = selectedFile.getAbsolutePath();

            if(!fname.endsWith(".billdb") ) {
                selectedFile = new File(fname + ".billdb");
            }
            return selectedFile;
        }
        else if (result == JFileChooser.CANCEL_OPTION){
            return null;
        }
        else{
            throw new IOException();
        }
    }
    
    /**
     * 
     * Shows about application dialog
     */
    public void showHelpOptionPane()
    {
        String message = "Tomasz Domaracki\n"
                        + "Java Bills Collection\n"
                        + "v. 1.0.0";
        JOptionPane infoBox = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog infoDialog = infoBox.createDialog(infoBox, message);
        infoDialog.setTitle("About");
        infoDialog.setVisible(true);
    }
    
    /**
     * Saves database to file
     * 
     * @param path path to file
     */
    public void saveDatabaseToFile(String path)
    {
        try{
            db.saveDatabaseToFile(path);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Opens database from file
     * 
     * @param path path to file
     * @return database tree model for JTree
     */
    public DefaultTreeModel openDatabaseFromFile(String path){
        try{
            db.readDatabaseFromFile(path);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            
            String message = "This database is not supported. Please select another.";
            JOptionPane infoBox = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
            JDialog infoDialog = infoBox.createDialog(infoBox, message);
            infoDialog.setTitle("Info");
            infoDialog.setVisible(true);
            return new DefaultTreeModel(new DefaultMutableTreeNode("Open database"));
        }
        
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Bills from database");
        int dbSize = db.getDatabaseSize();
        for(int i=0;i<dbSize;++i){
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(db.getFromDatabase(i));
            top.add(node);
        }      
        return new DefaultTreeModel(top);  
    }
    
    /**
     * Creates database tree model for JTree from database variable
     * 
     * @return database tree model for JTree
     */
    public DefaultTreeModel openDatabase(){
        
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Bills from database");
        int dbSize = db.getDatabaseSize();
        for(int i=0;i<dbSize;++i){
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(db.getFromDatabase(i));
            top.add(node);
        }      
        return new DefaultTreeModel(top);  
    }
    
    /**
     * Shows dialog for editing existing bill in database
     * 
     * @param mainFrame application frame
     * @param node object selected in JTree
     * @param parentLayout object to center new created window
     * @return dialog status (which button was clicked)
     */
    public int showEditBillDialog(java.awt.Frame mainFrame, DefaultMutableTreeNode node, JSplitPane parentLayout){
        try{
            
            Bill selectedBill = (Bill) node.getUserObject();

            EditBillDialog dialog = new EditBillDialog(mainFrame, true, parentLayout, db);
            dialog.setSelectedBill(selectedBill);
            dialog.initTextBoxes();
            dialog.showBillPhoto();
            dialog.setLocationRelativeTo(parentLayout);
            dialog.setVisible(true);

            return dialog.getReturnStatus();
        }
        catch(NullPointerException | ClassCastException exception){
            String message = "Please select bill to edit first";
            JOptionPane infoBox = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
            JDialog infoDialog = infoBox.createDialog(infoBox, message);
            infoDialog.setTitle("Info");
            infoDialog.setVisible(true);
            
            return 0;
        }
    }
    /**
     * 
     * 
     * @param parentLayout
     * @param defaultDbPath 
     */
    public void saveDatabaseOnServer(javax.swing.JSplitPane parentLayout, String defaultDbPath){
        ServerConnectionController serverConnection;
        AppLogger logger = AppLogger.getInstance();
        try{
            PleaseWaitInfo infoDialog = showPleaseWaitDialog(parentLayout);
            
            serverConnection = new ServerConnectionController(defaultDbPath, getAllBillPhotosPaths(), infoDialog);
            serverConnection.execute();
        }
        catch(IOException ex){
            logger.addTextToLog(ex.getMessage());
        }
    }
    
    private PleaseWaitInfo showPleaseWaitDialog(javax.swing.JSplitPane parentLayout){
        PleaseWaitInfo infoDialog = new PleaseWaitInfo();
        infoDialog.setLocationRelativeTo(parentLayout);
        infoDialog.setVisible(true);
        return infoDialog;
        
    }
    
    public List<String> getAllBillPhotosPaths(){
        return db.getPhotosPaths();
    }
    
    /**
     * Shows warning dialog before bill deleting and if okey deletes selected one
     * 
     * @param node selected object in JTree
     * @return return if selected to delete bill or not
     */
    public int showDeleteDialogAndDeleteIfOk(DefaultMutableTreeNode node){
        
        try{
            
            Bill selectedBill = (Bill) node.getUserObject();

            String message = "Do you really want to delete bill " + selectedBill.getName() + " ?";
            
            int result = JOptionPane.showConfirmDialog(null, message, message, JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION){
            try{
                Files.deleteIfExists(Paths.get(selectedBill.getPhotoFile().getPath()));
            }
            catch(IOException ex){
                System.err.println(ex.getMessage());
            }
                db.deleteFromDatabase(selectedBill);
                return 1;
            }
            else{
                return 0;
            }
        }
        catch(NullPointerException | ClassCastException exception){
            
            String message = "Please select bill to delete first";
            JOptionPane infoBox = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
            JDialog infoDialog = infoBox.createDialog(infoBox, message);
            infoDialog.setTitle("Info");
            infoDialog.setVisible(true);
            return 0;
            
        }
    }
    
}
