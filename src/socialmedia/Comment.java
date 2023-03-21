package socialmedia;


/**
 * This comment class is used to create objects that represents comments on a post
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class Comment extends ActionablePost{

    /**
     * A public static field {@link Integer} that stores the number of comment posts on the social media platform
     */
    public static int numberComments = 0;

    /**
     * A private field {@link EmptyPost} that stores a post that has been commented on
     */
    private EmptyPost commentedPost;


    /**
     * A constructor that creates a new comment object
     * @param poster the account of the commenter
     * @param commentedPost the post that the commenter is posting the comment in
     * @param message the message the comment contains
     * @throws InvalidPostException when trying to create an invalid post
     * @throws NotActionablePostException when trying to act upon an non-actionable post
     */
    public Comment(Account poster, Post commentedPost, String message) throws InvalidPostException, NotActionablePostException{
        // Use the constructor of the super class to set the id of the comment
        super();
        
        // A pre condition checks if the commented post is actionable or if it has a valid message
        isValidMessage(message);
        isActionable(commentedPost);

        // The commented post, poster and message are set
        this.commentedPost = commentedPost;
        this.poster = poster;
        this.message = message;

        // The number of comments are incremented by 1
        numberComments++;

        // The comment is added to the original post list of comments and added to the posters list of posts
        this.poster.addPost(this);
        ((ActionablePost)commentedPost).addComment(this);
    }

    /**
     * This method is used to set the original post
     * @param commentedPost the post to be set
     */
    public void setCommentedPost(EmptyPost commentedPost){
        this.commentedPost = commentedPost;
    }


    @Override
    public void delete(){
        // use the super method to handle the list of comments and endorsements
        super.delete();
        
        // A variable is set for the post condition
        int originalNumberOfComments = numberComments;

        // The comment is removed from the original post list of comments
        if(commentedPost instanceof ActionablePost){
            ((ActionablePost)commentedPost).removeComment(this);
        }

        // The comment is removed from the posters list of comments
        poster.removePost(this);

        // The number of comments is decremented by 1
        numberComments--;
        
        // Assertion checks that the post condition is met and if it is not met then it throws an exception
        assert (originalNumberOfComments - 1 == numberComments):"Comment not successfully deleted";
    }

    /**
     * Resets the counters for comment
     */
    static public void resetCounters(){
        numberComments = 0;
    }
}
