package socialmedia;
/**
 * This comment class is used to create objects that represents comments in a post
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class Comment extends Post{
    /**
     * A private field {@link Post} that stores a post that has been commented on
     */
    private Post originalPost;
    /**
     * A constructor that creates a new comment object
     * @param poster the account of the commenter
     * @param originalpost the post that the commenter is posting the comment in
     * @param message the message the comment contains
     */
    public Comment(Account poster, Post originalpost, String message) throws InvalidPostException, PostIDNotRecognisedException, NotActionablePostException{
        //TODO
        // Use the constructor of the super class to partially initiliase the comment
        super(poster, message);
        // the original post get set
        this.originalPost = originalpost;
        // the comment is added to the original post list of comments
        originalpost.addComment(this);
    }
    /**
     * This method is used to set the original post
     * @param originalPost the post to be set
     */
    public void setOriginalPost(Post originalPost){
        this.originalPost = originalPost;
    }
    /**
     * This method overrides the delete method from posts and deletes the comment
     */
    @Override
    public void delete(){
        // use the super method of deleting to partially remove the comment
        super.delete();
        // the comment is remove from the original post list of comments
        originalPost.removeComment(this);
        // the link to the original post is removed
        originalPost = null;
    }
}
