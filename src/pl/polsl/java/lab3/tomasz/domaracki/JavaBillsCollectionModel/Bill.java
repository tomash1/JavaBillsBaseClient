package pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionModel;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class implement bill object to use in project
 * 
 * @author tomaszdomaracki
 * @version 1.0.0
 */
public class Bill implements Serializable{
    /**
     * The Date representing date of guarantee on bill
     */
    private Date dateOfGuarantee;
    /**
     * The string representing name of bill photo
     */
    private File billPhoto;
    /**
     * The string representing name of bill
     */
    private final String billName;
    /**
     * Integer representing time of guarantee
     */
    private int guaranteeDuration;
    /**
     * The int used to create bill name
     */
    private static int nameIterator = 0;
    
    /**
     * The initiating constructor of the class Bill without arguments
     */
    public Bill(){
        dateOfGuarantee = new Date();
        billName = "Bill" + nameIterator;
        guaranteeDuration = 0;
        ++nameIterator;
    }
    
    /**
     * The initiating constructor of the class Bill with argument
     * 
     * @param billName string representing bill name
     */
    public Bill(String billName){
        
        dateOfGuarantee = new Date();
        this.billName = billName;
        guaranteeDuration = 0;
    }
    /**
     * Override toString method
     * 
     * @return bill name
     */
    @Override
    public String toString(){
        return billName;
    }
    /**
     * Getter for dateOfGuarantee var
     * 
     * @return dateOfGuarantee (type Date)
     */
    public Date getDateOfGuarantee(){
        return this.dateOfGuarantee;
    }
    /**
     * Getter for billName var
     * 
     * @return billName (type String)
     */
    public String getName(){
        return this.billName;
    }
    /**
     * Getter for billPhoto var
     * 
     * @return billPhoto (type String)
     */
    public File getPhotoFile(){
        return this.billPhoto;
    }
    /**
     * Setter for billPhoto var
     * 
     * @param billPhoto representing bill photo
     */
    public void setPhotoFile(File billPhoto){
        this.billPhoto = billPhoto;
    }
    /**
     * Getter for guaranteeDuration var
     * 
     * @return billPhoto (type String)
     */
    public int getGuaranteeDuration(){
        return guaranteeDuration;
    }
    /**
     * Setter for billPhoto var
     * 
     * @param guaranteeDuration int representing guarantee duration
     */
    public void setGuaranteeDuration(int guaranteeDuration){
        this.guaranteeDuration = guaranteeDuration;
    }
    /**
     * Getter for dateOfGuarantee var
     * 
     * @return dateOfGuarantee (type String)
     */
    public String getDateOfGuaranteeToString(){
       
        if (this.dateOfGuarantee != null){
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            return outputFormat.format(this.dateOfGuarantee);
        }
        else{
            throw new NullPointerException("Date is not set !");
        }
    }
        /**
     * Getter for dateOfGuarantee var
     * 
     * @return dateOfGuarantee (type String)
     */
    public String getDateOfGuaranteeInInputFormat(){
       
        if (this.dateOfGuarantee != null){
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
            return outputFormat.format(this.dateOfGuarantee);
        }
        else{
            throw new NullPointerException("Date is not set !");
        }
    }
    /**
     * Setter for dateOfGuarantee var
     * 
     * @param dateOfGuarantee Date representing date of guarantee for new bill
     */
    public void setDateOfGuarantee(Date dateOfGuarantee){
        
        this.dateOfGuarantee = dateOfGuarantee;
    }
    /**
     * Setter for dateOdGuarantee var
     * 
     * @param dateAsString string representing date of guaratnee for new bill to parse
     */
    public void setDateOfGuarantee(String dateAsString){
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        
        try{
            this.dateOfGuarantee = simpleDateFormat.parse(dateAsString);
        }
        catch(ParseException exception){
            System.err.println("Wrong date format. Must be: " + simpleDateFormat.toPattern());
            this.dateOfGuarantee = null;
            throw new NullPointerException();
        }
    }
}
