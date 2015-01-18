package pl.polsl.java.lab3.tomasz.domaracki.JavaBillsCollectionView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Class verify user input before save bill information in database
 * 
 * @author tomaszdomaracki
 * @version 1.0.0
 */
public class VerifyUserInput {

    /**
     * String representing how long the guarantee is valid
     */
    private final String durationOfGuarantee;
    /**
     * String representing guarantee start date
     */
    private final String dateOfGuarantee;
    
    /**
     * The initiating constructor of the class VerifyUserInput
     * 
     * @param durationOfGuarantee how long guarantee is valid
     * @param dateOfGuarantee start date of guarantee
     */
    public VerifyUserInput(String durationOfGuarantee, String dateOfGuarantee){
        this.durationOfGuarantee = durationOfGuarantee;
        this.dateOfGuarantee = dateOfGuarantee;
    }
    
    /**
     * Verifies if date format is valid
     * 
     * @return result
     */
    public boolean verifyDateOfGuarantee(){
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try{
            simpleDateFormat.parse(dateOfGuarantee);
        }
        catch(ParseException exception){
            return false;
        }
        return true;
    }
    
    /**
     * Verify id guarantee duration is valid (integer number)
     * 
     * @return result
     */
    public boolean verifyDurationOfGuarantee(){
        try{
            Integer.parseInt(durationOfGuarantee);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
}
