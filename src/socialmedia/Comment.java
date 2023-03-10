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
    /**
     * A constructor that creates a new comment object
     * @param poster the account of the commenter
     * @param commentedPost the post that the commenter is posting the comment in
     * @param message the message the comment contains
     */
    public Comment(Account poster, Post commentedPost, String message) throws InvalidPostException, NotActionablePostException{
        //TODO
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
    }

    @Override
    //TODO
    public void resetCounters(){
        super.resetCounters();
        numberComments = 0;
    }

}
