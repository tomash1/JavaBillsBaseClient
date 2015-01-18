package pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionController;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionModel.Bill;
import pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionModel.TemporaryDatabase;

/**
 * Class designed to become a model in MVC architecture
 * Manages all bills in app
 *
 * @author tomaszdomaracki
 * @version 1.0.0
 */
public class BillController {
    /**
     * The TemporaryDatabase representing implemented DB for testing
     */
    private final TemporaryDatabase database;
    /**
     * The initiating constructor of the class BillManager
     */
    public BillController(){
        
        database = new TemporaryDatabase();
    }
    /**
     * Creates new bills 
     * 
     * @return new object of type Bill
     */
    public Bill createNewBill(){
        
        return new Bill(); 
    }
   
    /**
     * Creates new bills with given name
     * 
     * @param newBillName string representing new bill name
     * @param dateOfGuarantee date of guarantee on bill
     * @param billPhoto bill photo file
     * @param guaranteeDuration duration of guarantee for product on bill
     * @return new object of type Bill
     */
    public Bill createNewBill(String newBillName, String dateOfGuarantee, File billPhoto, int guaranteeDuration){
        
        if ("".equals(newBillName)){
            Bill createdBill = new Bill();
            createdBill.setDateOfGuarantee(dateOfGuarantee);
            createdBill.setPhotoFile(billPhoto);
            createdBill.setGuaranteeDuration(guaranteeDuration);
            return createdBill;
        }
        else{
            Bill createdBill = new Bill(newBillName);
            createdBill.setDateOfGuarantee(dateOfGuarantee);
            createdBill.setPhotoFile(billPhoto);
            createdBill.setGuaranteeDuration(guaranteeDuration);
            return createdBill;
        }
    }
    /**
     * Creates new bills and gets dateOfGuarantee from console
     * 
     * @return new object of type Bill
     */
    public Bill createNewBillAndGetDateFromUser(){
        
        Bill createdBill = new Bill();
       
        String billDate = this.getDateInStringFormatFromConsole();
        try{
            createdBill.setDateOfGuarantee(billDate);
        }
        catch(NullPointerException e){
            throw new NullPointerException("Can not create new bill !");
        }
       return createdBill;
    }
    /**
     * Creates new bills with given name and gets dateOfGuarantee from console
     * 
     * @param newBillName string representing new bill name
     * @return new object of type Bill
     */
    public Bill createNewBillAndGetDateFromUser(String newBillName){
        
        Bill createdBill = new Bill(newBillName);
       
        String billDate = this.getDateInStringFormatFromConsole();
        try{
            createdBill.setDateOfGuarantee(billDate);
        }
        catch(NullPointerException e){
            throw new NullPointerException("Can not create new bill !");
        }
       return createdBill;
    }
    /**
     * Gets date string from console (YYYY/MM/DD)
     * 
     * @return date in string
     */
    public String getDateInStringFormatFromConsole(){
        
        System.out.println("Write bill date: YYYY/MM/DD");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
    /**
     * Adds bill to database
     * 
     * @param newBill bill to add to database
     */
    public void addBillToDatabase(Bill newBill){
        
        database.addToDatabase(newBill);
    }
    /**
     * Getter for all bills inside database
     * 
     * @return Bill array with bills
     */
    public Bill[] getAllBillsFromDatabase(){
        
        List<Bill> billsToReturn = new ArrayList<>();
        int baseLength = database.getDatabaseSize();
        
        for(int i=0;i<baseLength;++i){
            billsToReturn.add((Bill)database.getFromDatabase(i));
        }
        return billsToReturn.toArray(new Bill[billsToReturn.size()]);
    }
    /**
     * Getter for bills from database with given dateOfGuarantee
     * 
     * @param date to search bills inside database
     * @return Bill array with bills
     */
    public Bill[] getBillsFromDatabaseByStringDate(String date){
        
        List<Bill> billsToReturn = new ArrayList<>();
        int baseLength = database.getDatabaseSize();
        SimpleDateFormat dateConverter = new SimpleDateFormat("yyyy/MM/dd");
        Date parsedDate;
        
        try{
            parsedDate = dateConverter.parse(date);
        }
        catch(ParseException e){
            System.out.println("Wrong date format. Must be: " + dateConverter.toPattern());
            return null;
        }
        
        for(int i =0;i<baseLength;++i){
            Bill billFromDatabase = (Bill)database.getFromDatabase(i);   
            if (billFromDatabase.getDateOfGuarantee().equals(parsedDate)){
                billsToReturn.add(billFromDatabase);
            }
        }
        
        return billsToReturn.toArray(new Bill[billsToReturn.size()]);
    }
}
