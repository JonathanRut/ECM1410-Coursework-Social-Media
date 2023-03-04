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
     * This method gets the number of endorsements on the post
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

    /**
     * Resets the static counters for post
     */
    public void resetCounters(){
        numberPosts = 0;
        currentId = 0;
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
    @Override
    /**
     * This method returns a string of information about the Individual post
     * @return the string of information about the post 
     */
    public String toString(){
        // A new StringBuilder is created and then information about the Individual post is appended to it
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ID: ").append(ID).append("\n");
		stringBuilder.append("Account: ").append(poster.getHandle()).append("\n");
		stringBuilder.append("No. endorsements: ").append(getNumberOfEndorsements()).append(" | ");
		stringBuilder.append("No. comments: ").append(comments.size()).append("\n");
		stringBuilder.append(message);
        // The information is returned as a string
        return stringBuilder.toString();
    }


    /**
     * This method returns a string of information about the Individual post with a given indent
     * @param indent the number of indents needed
     * @return the string of information about the post 
     */
    public String toString(int indent){
        // A new StringBuilder is created and then information about the Individual post is appended to it
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ".repeat((indent - 1) * 4) + "| > ").append("ID: ").append(ID).append("\n");
		stringBuilder.append(" ".repeat(indent * 4)).append("Account: ").append(poster.getHandle()).append("\n");
		stringBuilder.append(" ".repeat(indent * 4)).append("No. endorsements: ").append(getNumberOfEndorsements()).append(" | ");
		stringBuilder.append("No. comments: ").append(comments.size()).append("\n");
		stringBuilder.append(" ".repeat(indent * 4)).append(message);
        // The information is returned as a string
        return stringBuilder.toString();
    }

    /**
     * This method recursively generates a string builder with details of the current posts and its children posts
     * @param indent the indent to be applied to the details of the children
     * @return A string builder of the children posts
     */
    public StringBuilder showChildren(int indent){
        // A new string builder is instantiated
        StringBuilder stringBuilder = new StringBuilder();

        // If this is the current post then no indent is needed
        if(indent == 0){
            stringBuilder.append(toString());
        }
        // Otherwise an indent is applied to the details
        else{
            stringBuilder.append(toString(indent));
        }
        stringBuilder.append("\n");
        // This for loop iterates through the comments of the post
        for(int i = 0; i < comments.size(); i++){
            // If it is the first comment then a bar is added at the tops
            if(i == 0){
                stringBuilder.append("    ".repeat(indent)+"|\n");
            }
            // Then the deatils about the comments children is gotten from the comment with an increased indent
            stringBuilder.append(comments.get(i).showChildren(indent + 1).toString());
        }
        // Finally the string builder is returned
        return stringBuilder;
    }
}

