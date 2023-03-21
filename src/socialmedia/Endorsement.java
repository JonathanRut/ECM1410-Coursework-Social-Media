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


    /**
     * A constructor that creates a new endorsement object
     * @param poster the account that is endorsing the post
     * @param endorsedPost the post that is being endorsed
     * @throws NotActionablePostException when trying to act upon an non-actionable post
     */
    public Endorsement(Account poster, Post endorsedPost) throws NotActionablePostException{
        // Use the constructor of the super class sets the id of the post
        super();

        // A pre condition checks that if the post being endorsed is actionable
        isActionable(endorsedPost);

        // The post being endorsed is set along with the poster and message
        this.endorsedPost = (ActionablePost)endorsedPost;
        this.poster = poster;
        this.message = endorsedPost.message;

        // The post is added to the posters list of posts
        this.poster.addPost(this);

        // The endorsement gets added to the endorsed post endorsements list
        ((ActionablePost)endorsedPost).addEndorsement(this);

        // This gets the account that is being endorsed and increment the number of endorsements by 1
        Account endorsedAccount = endorsedPost.getPoster();
        endorsedAccount.incrementEndorsements();

        // The number of endorsements is incremented by 1
        numberEndorsements++;
    }


    @Override
    public void delete(){
        // A new variable is created for asserting the endorsement was deleted
        int numberOfOriginalEndorsements = numberEndorsements;

        // The account that was endorsed is retrieved and its number of endorsements is decremented by 1
        Account endorsedAccount = endorsedPost.getPoster();
        endorsedAccount.decrementEndorsements();
        

        // The endorsement is removed from the posters and the endorsed post list of endorsements
        poster.removePost(this);
        endorsedPost.removeEndorsement(this);
        
        // The number of endorsements is decremented by 1
        numberEndorsements--;

        // Assertion checks that the post condition is met and if it is not met then it throws an exception
        assert (numberOfOriginalEndorsements -1 == numberEndorsements):"Endorsement not deleted successfully";
    }

    /**
     * Resets the static counters in Endorsement
     */
    static public void resetCounters() {
        numberEndorsements = 0;
    }

    @Override
    public String toString(){
        // A new StringBuilder is created and then information about the endorsement post is appended to it
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
