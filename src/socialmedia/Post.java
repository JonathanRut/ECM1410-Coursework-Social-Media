package socialmedia;

import java.util.ArrayList;

public class Post {
    private int id;
    private String message;
    private Account poster;
    private ArrayList<Post> endorsements;
    private ArrayList<Post> comments;
    private static int CURRENT_ID=0;
    public static int NUMBER_POSTS=0;

    public Post (Account poster, String message){

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

