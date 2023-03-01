package socialmedia;

import java.util.ArrayList;

/**
 * The post class is used to create objects that represent users original posts on social media
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class Post {
     /**
     * A private constant {@link Integer} used to store the id of a post this is unique to the post
     */
    private final int ID;
    /**
     * A private variable {@link String} used to store the message for the post
     */
    private String message;
    /**
     * A private variable {@link Account} used to store the account who posted the post
     */
    private Account poster;
    /**
     * A private list {@link ArrayList} containing elements of type {@link Endorsement} used to store the endorsements of a post
     */
    private ArrayList<Endorsement> endorsements = new ArrayList<Endorsement>();
    /**
     * A private list {@link ArrayList} containing elememts of type {@link Comment} used to store the comment of a post
     */
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    /**
     * A private variable {@link Integer} that stores the id of the next post to be created
     */
    private static int currentId=0;
    /**
     * A private variable {@link Integer} that stores the number of posts on the social media platform
     */
    public static int numberPosts=0;
    /**
     * A public constructor that creates a new post object
     * @param poster is the account that has posted the post
     * @param message is the message that the account has written in the post
     */
    public Post(Account poster, String message){
        // fields get given their values
        this.poster = poster;
        this.message = message;
        this.ID = currentId;
        // static counters get incremented
        currentId++;
        numberPosts++;
        // Post is getting added to accounts list of posts
        poster.addPost(this);
    }
    /**
     * This method gets the id and then returns the id of the post
     * @return the id of the post
     */
    public int getId(){
        return this.ID;
    }
    /**
     * This method gets the message and then returns the mesage of the post
     * @return the message of the message
     */
    public String getMessage(){
        return this.message;
    }
    /**
     * This method deletes the post and then removes it from the arraylist and decreases the numberPosts counter by 1
     */
    public void delete(){
        // For each Endorsement in the endorsemens list, the endorsement is deleted
        for(Endorsement endorsement : endorsements){
            endorsement.delete();
        }
        // For each Comment in the comments list, the link to the post is removed 
        for(Comment comment : comments){
            comment.setOriginalPost(null);
        }
        //this removes the post from the account it was posted from
        poster.removePost(this);
        // this decreases the numberPosts counter by 1
        numberPosts--;
    }
    /**
     * This method gets the number of endorsements in the endorsements list
     * @return the number of endorsements returned
     */
    public int getNumberOfEndorsements(){
        return endorsements.size();
    }
    /**
     * This method gets the account that has posted the post
     * @return the account that has posted the post
     */
    public Account getPoster(){
        return poster;
    }

    public void resetCounters(){
        
    }
    /**
     * This method adds an endorsement to the endorsements list
     * @param endorsement the endorsement to be added
     */
    public void addEndorsement(Endorsement endorsement){
        endorsements.add(endorsement);
    }
    /**
     * This method adds a comment to the comments list
     * @param comment the comment to be added 
     */
    public void addComment(Comment comment){
        comments.add(comment);
    }
    /**
     * This method removes an endorsement from the endorsements list 
     * @param endorsement the endorsement to be removed
     */
    public void removeEndorsement(Endorsement endorsement){
        // this for loop interates through each element in the endorsements list
        int index = -1;
        for(int i = 0; i < endorsements.size(); i++){
            // this if statement gets the index of the endorsement by comparing the ith element in the endorsement list
            if(endorsements.get(i) == endorsement){
                index = i;
            }
        }
        // this removes the endorsement from the endorsement list
        endorsements.remove(index);
    }
    /**
     * This method removes a comment from the comments list
     * @param comment the comment to be removed
     */
    public void removeComment(Comment comment){
        // this for loop iterates through each element in the comments list
        int index = -1;
        for(int i = 0; i < comments.size(); i++){
            // this if statement gets the index of the comment by comparing the ith element in the comments list
            if(comments.get(i) == comment){
                index = i;
            }
        }
        // this removes the comments from the comments list
        comments.remove(index);
    }
    
    public String toString(){
        return "";
    }

    public StringBuilder showChildren(){
    return new StringBuilder();

    }
}

