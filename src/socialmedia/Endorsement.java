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
    //TODO    
    public static int numberEndorsements=0;

    /**
     * A constructor that creates a new endorsement object
     * @param poster the account that is endorsing the post
     * @param endorsedPost the post that is being endorsed
     */
    public Endorsement(Account poster, Post endorsedPost) throws NotActionablePostException{
        //TODO
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
        //TODO
        numberEndorsements++;
    }
    /**
     * This method overrides the delete method from posts and deletes the endorsement
     */
    @Override
    public void delete(){
        int numberOfOriginalEndorsements = numberEndorsements;
        Account endorsedAccount = endorsedPost.getPoster();
        int numberOfOriginalEndorsementAccount = endorsedAccount.getEndorsements();
        // Removing the endorsement from the endorsed posts endorsements list
        endorsedPost.removeEndorsement(this);
        // Decrementing the number of endorsements by 1
        numberEndorsements--;
        // Getting the accoubnt that is being endorsed and decrement the number of endorsements by 1
        endorsedAccount.decrementEndorsements();

        assert (numberOfOriginalEndorsements -1 == numberEndorsements && !endorsedPost.endorsements.contains(this) && numberOfOriginalEndorsementAccount -1 == endorsedAccount.getEndorsements()):"Endorsement not deleted successfully";
    }

    @Override
    public void resetCounters() {
        // TODO
        super.resetCounters();
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
