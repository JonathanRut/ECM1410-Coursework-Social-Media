package socialmedia;
/**
 * This comment class is used to create objects that represents comments in a post
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class Comment extends ActionablePost{
    public static int numberComments = 0;
    /**
     * A private field {@link Actionable} that stores a post that has been commented on
     */
    private EmptyPost commentedPost;

    private static final long serialVersionUID = 834344499346955094l;

    /**
     * A constructor that creates a new comment object
     * @param poster the account of the commenter
     * @param commentedPost the post that the commenter is posting the comment in
     * @param message the message the comment contains
     * @throws InvalidPostException when trying to create an invalid post
     * @throws NotActionablePostException when trying to act upon an non-actionable post
     */
    public Comment(Account poster, Post commentedPost, String message) throws InvalidPostException, NotActionablePostException{
        // Use the constructor of the super class to partially initiliase the comment
        super();
        isValidMessage(message);
        isActionable(commentedPost);
        // the original post get set
        this.commentedPost = commentedPost;
        this.poster = poster;
        this.message = message;
        numberComments++;
        // the comment is added to the original post list of comments
        
        ((ActionablePost)commentedPost).addComment(this);
    }
    /**
     * This method is used to set the original post
     * @param commentedPost the post to be set
     */
    public void setCommentedPost(EmptyPost commentedPost){
        this.commentedPost = commentedPost;
    }
    /**
     * This method overrides the delete method from posts and deletes the comment
     */
    @Override
    public void delete(){
        // use the super method of deleting to partially remove the comment
        super.delete();
        // the comment is remove from the original post list of comments
        ((ActionablePost)commentedPost).removeComment(this);
        // the link to the original post is removed
        commentedPost = new EmptyPost();
        // Assertion checks that the post condition is met and if it is not met then it throws an exception
        assert (commentedPost.getMessage().equals("The original content was removed from the system and is no longer available.")):"Comment not successfully deleted";
    }

    /**
     * Resets the counters for comment
     */
    static public void resetCounters(){
        numberComments = 0;
    }
}
