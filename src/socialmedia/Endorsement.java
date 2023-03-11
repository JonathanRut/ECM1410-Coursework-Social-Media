package socialmedia;


/**
 * This endorsement class is used to create endorsement objects that represent endorsements from posts
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class Endorsement extends Post{
    /**
     * A private field {@link ActionablePost} that stores the post that is being endorsed
     */
    private ActionablePost endorsedPost;
    /**
     * A public static {@link Integer} that stores the number of endorsements in the system
     */
    public static int numberEndorsements=0;

    private static final long serialVersionUID = 7475185244668276409l;

    /**
     * A constructor that creates a new endorsement object
     * @param poster the account that is endorsing the post
     * @param endorsedPost the post that is being endorsed
     * @throws NotActionablePostException when trying to act upon an non-actionable post
     */
    public Endorsement(Account poster, Post endorsedPost) throws NotActionablePostException{
        // Use the constructor of the super class to set the poster and the mesage of the endorsedpost
        super();
        isActionable(endorsedPost);
        // The post being endorsed gets set
        this.endorsedPost = (ActionablePost)endorsedPost;
        this.poster = poster;
        this.message = endorsedPost.message;
        // The endorsement gets added to the originals post endorsements list
        ((ActionablePost)endorsedPost).addEndorsement(this);
        // Getting the account that is being endorsed and increment the number of endorsements by 1
        Account endorsedAccount = endorsedPost.getPoster();
        endorsedAccount.incrementEndorsements();
        numberEndorsements++;
    }
    /**
     * This method overrides the delete method from posts and deletes the endorsement
     */
    @Override
    public void delete(){
        // A new variable is created for asserting the endorsement was deleted
        int numberOfOriginalEndorsements = numberEndorsements;
        //Getting the account that is being endorsed
        Account endorsedAccount = endorsedPost.getPoster();
        // A new variable is created for asserting the endorsement was deleted
        int numberOfOriginalEndorsementAccount = endorsedAccount.getEndorsements();
        // Removing the endorsement from the endorsed posts endorsements list
        endorsedPost.removeEndorsement(this);
        // Decrementing the number of endorsements by 1
        numberEndorsements--;
        // Decrements the number of endorsements by 1
        endorsedAccount.decrementEndorsements();
        // Assertion checks that the post condition is met and if it is not met then it throws an exception
        assert (numberOfOriginalEndorsements -1 == numberEndorsements && !endorsedPost.endorsements.contains(this) && numberOfOriginalEndorsementAccount -1 == endorsedAccount.getEndorsements()):"Endorsement not deleted successfully";
    }

    /**
     * Resets the static counters in Endorsement
     */
    static public void resetCounters() {
        numberEndorsements = 0;
    }

    @Override
    public String toString(){
        // A new StringBuilder is created and then information about the Individual post is appended to it
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ID: ").append(ID).append("\n");
		stringBuilder.append("Account: ").append(poster.getHandle()).append("\n");
		stringBuilder.append("No. endorsements: ").append(0).append(" | ");
		stringBuilder.append("No. comments: ").append(0).append("\n");
		stringBuilder.append(message);
        // The information is returned as a string
        return stringBuilder.toString();
    }


}
