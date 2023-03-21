package socialmedia;

/**
 * The original post class is used to create objects that represent users original posts on social media
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class OriginalPost extends ActionablePost{
    
    /**
     * A public static field {@link Integer} that stores the number of original posts on the social media platform
     */
    public static int numberOriginalPosts = 0;


    /**
     * A public constructor that creates a new post object
     * @param poster is the account that has posted the post
     * @param message is the message that the account has written in the post
     * @throws InvalidPostException if the message is empty or has more than 100 characters
     */
    public OriginalPost(Account poster, String message) throws InvalidPostException{
        // The super class constructor is used to set id of the post
        super();
        
        // A pre condition checks if the message of the post is valid
        isValidMessage(message);

        // The poster and message of the post are set
        this.poster = poster;
        this.message = message;

        // The number of posts is incremented
        numberOriginalPosts++;

        // Post is gets added to posters list of posts
        poster.addPost(this);
    }
    
    @Override
    public void delete(){
        // A new variable is created for asserting the OriginalPost was deleted
        int numberOfOriginalOriginalPosts = numberOriginalPosts;

        // The super class delete is run ensuring that the comments and endorsements are handled accordingly
        super.delete();

        // This decreases the numberPosts counter by 1
        numberOriginalPosts--;

        // The post is removed from the posters list of posts
        poster.removePost(this);
        
        // Assertion checks that the post condition is met and if it is not met then it throws an exception
        assert (numberOfOriginalOriginalPosts - 1 == numberOriginalPosts):"Original post not deleted successfully";
    }

    /**
     * Resets the static counters for post
     */
    static public void resetCounters(){
        numberOriginalPosts = 0;
    }


}

