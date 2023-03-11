package socialmedia;

/**
 * The post class is used to create objects that represent users original posts on social media
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class OriginalPost extends ActionablePost{
    
    /**
     * A public variable {@link Integer} that stores the number of orginal posts on the social media platform
     */
    public static int numberOriginalPosts = 0;

    private static final long serialVersionUID = 5983138584552336725l;

    /**
     * A public constructor that creates a new post object
     * @param poster is the account that has posted the post
     * @param message is the message that the account has written in the post
     */
    public OriginalPost(Account poster, String message) throws InvalidPostException{
        super();
        isValidMessage(message);
        this.poster = poster;
        this.message = message;
        numberOriginalPosts++;
        // Post is getting added to accounts list of posts
        poster.addPost(this);
    }
    
    @Override
    /**
     * This method deletes the post and then removes it from the arraylist and decreases the numberPosts counter by 1
     */
    public void delete(){
        // A new variable is created for asserting the OriginalPost was deleted
        int numberofOriginalOriginalPosts = numberOriginalPosts;
        super.delete();
        // this decreases the numberPosts counter by 1
        numberOriginalPosts--;
        // Assertion checks that the post condition is met and if it is not met then it throws an exception
        assert (numberofOriginalOriginalPosts - 1 == numberOriginalPosts):"Original post not deleted successfully";
    }

    /**
     * Resets the static counters for post
     */
    static public void resetCounters(){
        numberOriginalPosts = 0;
    }


}

