package socialmedia;
/**
 * This endorsement class is used to create endorsement objects that represent endorsements from posts
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class Endorsement extends Post{
    /**
     * A private field {@link Post} that stores the post that is being endorsed
     */
    private Post endorsedPost;
    /**
     * A constructor that creates a new endorsement object
     * @param poster the account that is endorsing the post
     * @param endorsedPost the post that is being endorsed
     */
    public Endorsement(Account poster, Post endorsedPost) throws InvalidPostException, NotActionablePostException, PostIDNotRecognisedException{
        //TODO
        // Use the constructor of the super class to set the poster and the mesage of the endorsedpost
        // fields get given their values
        this.poster = poster;
        this.message = endorsedPost.getMessage();
        this.ID = currentId;
        // static counters get incremented
        currentId++;
        numberPosts++;
        // Post is getting added to accounts list of posts
        poster.addPost(this);
        // The post being endorsed gets set
        this.endorsedPost = endorsedPost;
        // The endorsement gets added to the originals post endorsements list
        endorsedPost.addEndorsement(this);
        // Getting the account that is being endorsed and increment the number of endorsements by 1
        Account endorsedAccount = endorsedPost.getPoster();
        endorsedAccount.incrementEndorsements();
    }
    /**
     * This method overrides the delete method from posts and deletes the endorsement
     */
    @Override
    public void delete(){
        // Removing the endorsement from the endorsed posts endorsements list
        endorsedPost.removeEndorsement(this);
        // Decrementing the number of endorsements by 1
        numberPosts--;
        // Getting the accoubnt that is being endorsed and decrement the number of endorsements by 1
        Account endorsedAccount = endorsedPost.getPoster();
        endorsedAccount.decrementEndorsements();
    }

}
