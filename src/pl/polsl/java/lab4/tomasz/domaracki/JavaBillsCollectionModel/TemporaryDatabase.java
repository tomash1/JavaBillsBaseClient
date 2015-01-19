package pl.polsl.java.lab4.tomasz.domaracki.JavaBillsCollectionModel;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;

/**
 * Class representing temporary database created for testing
 *
 * @author toamszdomaracki
 * @version 1.0.0
 */
public class TemporaryDatabase implements Serializable{
    /**
     * The List representing database 
     */
    private final List base;
    
    /**
     * The initiating constructor of the class TemporaryDatabase
     */
    public TemporaryDatabase(){
        base = new LinkedList();
    }
    
    /**
     * Adds new Bill to database
     * 
     * @param newObj object to add
     */
    public void addToDatabase(Object newObj){
        base.add(newObj);
    }
    
    /**
     * Gets object from database
     * 
     * @param index number of object to return
     * @return Object from database 
     */
    public Object getFromDatabase(int index){
        return base.get(index);
    }
    
    /**
     * Getter for database size
     * 
     * @return database size
     */
    public int getDatabaseSize(){
        return base.size();
    }
    /**
     * Deletes object from database
     * 
     * @param objectToDelete object to delete from database
     */
    public void deleteFromDatabase(Object objectToDelete){
        base.remove(objectToDelete);
    }
}
