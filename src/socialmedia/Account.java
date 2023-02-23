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
        this.handle = handle;
        this.id = CURRENT_ID;
        CURRENT_ID++;
        NUMBER_ACCOUNTS++;
    }

    public Account(String handle, String description){
        this.handle = handle;
        this.id = CURRENT_ID;
        this.description = description;
        CURRENT_ID++;
        NUMBER_ACCOUNTS++;
    }

    public String getHandle(){
        return handle;
    }

    public void setHandle(String handle){
        this.handle = handle;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String decsription){
        this.description = decsription;
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
        NUMBER_ACCOUNTS = 0;
        CURRENT_ID = 0;
    }
}
