package socialmedia;

/**
 * The post class is used to create objects that represent users original posts on social media
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class OriginalPost extends ActionablePost{
    
    /**
     * A private variable {@link Integer} that stores the number of posts on the social media platform
     */
    public static int numberOriginalPosts = 0;
    /**
     * A public constructor that creates a new post object
     * @param poster is the account that has posted the post
     * @param message is the message that the account has written in the post
     */
    public OriginalPost(Account poster, String message){
        super();
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
        super.delete();
        // this decreases the numberPosts counter by 1
        numberOriginalPosts--;
    }

    @Override
    /**
     * Resets the static counters for post
     */
    public void resetCounters(){
        super.resetCounters();
        numberOriginalPosts = 0;
        
    }


    
    

}

