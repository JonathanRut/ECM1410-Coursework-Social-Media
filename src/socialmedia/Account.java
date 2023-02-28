package socialmedia;

import java.util.ArrayList;

/**
 * The account class is used to create objects that represent users that can post to the social media
 * 
 * @author Jonathan Rutland & Daniel Stirling Barros
 * @version 1.0
 */
public class Account {
    
    /**
     * A public {@link Integer} representing the number of accounts that have been created
     */
    public static int NUMBER_ACCOUNTS = 0;
    private static int CURRENT_ID = 0;

    private String handle;
    private int id;
    private String description;
    private int numberOfEndorsements = 0;
    private ArrayList<Post> posts = new ArrayList<Post>();

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

    public void setDescription(String description){
        this.description = description;
    }

    public String toString(){
        return "";
    }

    public int getEndorsements(){
        return numberOfEndorsements;
    }

    public void incrementEndorsements(){
        numberOfEndorsements++;
    }

    public void decrementEndorsements(){
        numberOfEndorsements--;
    }

    public void delete(){
        for(Post post : posts){
            post.delete();
        }
        NUMBER_ACCOUNTS--;
    }

    public ArrayList<Post> getPosts(){
        return this.posts;
    }

    public void resetCounters(){
        NUMBER_ACCOUNTS = 0;
        CURRENT_ID = 0;
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public void removePost(Post post){
        int index = -1;
        for(int i = 0; i < posts.size(); i++){
            if(posts.get(i) == post){
                index = i;
            }
        }
        posts.remove(index);
    }
}
