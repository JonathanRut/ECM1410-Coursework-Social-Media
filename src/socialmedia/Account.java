package socialmedia;

import java.util.ArrayList;

public class Account {
    
    public static int NUMBER_ACCOUNTS = 0;
    private static int CURRENT_ID = 0;

    private String handle;
    private int id;
    private String description;
    private int numberOfEndorsements = 0;
    private ArrayList<Post> posts;

    public Account(String handle){

    }

    public Account(String handle, String description){

    }

    public String getHandle(){
        return "";
    }

    public void setHandle(String handle){

    }

    public int getId(){
        return 0;
    }

    public String getDescription(){
        return "";
    }

    public void setDescription(String decsription){

    }

    public String toString(){
        return "";
    }

    public void incrementEndorsements(){

    }

    public void decrementEndorsements(){

    }

    public void deleteAccount(){

    }

    public void resetCounters(){
        
    }
}
