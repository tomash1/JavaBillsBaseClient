
package pl.polsl.java.lab4.tomasz.domaracki.JavaBillsCollectionController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import pl.polsl.java.lab4.tomasz.domaracki.JavaBillsCollectionModel.Bill;
import pl.polsl.java.lab4.tomasz.domaracki.JavaBillsCollectionModel.TemporaryDatabase;

/**
 * Class designed to become a model in MVC architecture
 * Manages database in application
 * 
 * @author tomaszdomaracki
 * @version 1.0.0 
 */
public class DatabaseController {
    /**
     * The TemporaryDatabase representing implemented DB for testing
     */
    private TemporaryDatabase db;
    
    /**
     * The initiating constructor of the class DatabaseController
     */
    public DatabaseController() {
        
        db = new TemporaryDatabase();
    }
    
    /**
     * Adds new object to database
     * 
     * @param newObj new object to add
     */
    public void addToDatabase(Object newObj){
        db.addToDatabase(newObj);
    }
    
    /**
     * Gets object from database
     * 
     * @param index number of object to return
     * @return Object from database 
     */
    public Object getFromDatabase(int index){
        return db.getFromDatabase(index);
    }
    
    /**
     * Deletes given object from database
     * 
     * @param objectToDelete object to delete from database
     */
    public void deleteFromDatabase(Object objectToDelete){
        db.deleteFromDatabase(objectToDelete);
    }
    
    /**
     * Getter for database size
     * 
     * @return database size
     */
    public int getDatabaseSize(){
        return db.getDatabaseSize();
    }
    
    /**
     * Saves whole database in file with class serialization
     * 
     * @param path path to file
     * @throws IOException 
     */
    public void saveDatabaseToFile(String path) throws IOException{
        
        ObjectOutputStream fileToWriteObj;
        try {
            fileToWriteObj = new ObjectOutputStream(new FileOutputStream(path));
        } catch (IOException ex) {
            throw(ex);
        }
        
        fileToWriteObj.writeObject(db);
        fileToWriteObj.close();
    }
    
    /**
     * Reads database from file (class deserialization)
     * 
     * @param path path to database file
     * @throws IOException 
     */
    public void readDatabaseFromFile(String path) throws IOException{
        
        ObjectInputStream fileToReadObj;
        try {
            fileToReadObj = new ObjectInputStream(new FileInputStream(path));
        } catch (IOException ex) {
            throw(ex);
        }
        try{
            db = (TemporaryDatabase)fileToReadObj.readObject();
        }
        catch(ClassNotFoundException e){
            System.err.println(e.getMessage());
            fileToReadObj.close();
            throw new IOException();
        }
        
        fileToReadObj.close();
    }
    
    /**
     * Returns all bills paths from database
     * 
     * @return bills paths
     */
    public List<String> getPhotosPaths(){
        List<String> photosPaths = new ArrayList<>();
        
        for(int i=0;i<db.getDatabaseSize();i++){
            Bill billFromDb = (Bill)db.getFromDatabase(i);
            File billPhoto = billFromDb.getPhotoFile();
            photosPaths.add(billPhoto.getPath());
        }
        return photosPaths;
    }
}
