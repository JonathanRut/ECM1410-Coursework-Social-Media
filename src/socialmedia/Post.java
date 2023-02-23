package socialmedia;

import java.util.ArrayList;

public class Post {
    private int id;
    private String message;
    private Account poster;
    private ArrayList<Post> endorsements = new ArrayList<Post>();
    private ArrayList<Post> comments = new ArrayList<Post>();
    private static int CURRENT_ID=0;
    public static int NUMBER_POSTS=0;

    public Post(Account poster, String message){
        this.poster = poster;
        this.message = message;
        this.id = CURRENT_ID;
        CURRENT_ID++;
        NUMBER_POSTS++;
    }

    public int getId(){
        return this.id;
    }

    public String getMessage(){
        return this.message;
    }

    public void deletePost(){

    }

    public String toString(){
        return "";
    }

    public StringBuilder showChildren(){
        return new StringBuilder();

    }

    public int getNumberOfEndorsements(){
        return 1;
    }

    public Account getPoster(){
        return new Account("hello");
    }

    public void resetCounters(){
        
    }
}

