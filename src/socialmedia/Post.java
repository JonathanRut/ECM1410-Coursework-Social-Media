package socialmedia;

public abstract class Post extends EmptyPost {
    /**
     * A protected constant {@link Integer} used to store the id of a post this is unique to the post
     */
    protected final int ID;

    /**
     * A protected variable {@link Account} used to store the account who posted the post
     */
    protected Account poster;

    /**
     * A private variable {@link Integer} that stores the id of the next post to be created
     */
    public static int currentId=0;

    private static final long serialVersionUID = 5574749677956144457l;

    public Post(){
        this.ID = currentId;
        currentId++;
    }

    /**
     * This method gets the id and then returns the id of the post
     * @return the id of the post
     */
    public int getId(){
        return this.ID;
    }

    @Override
    public abstract String toString();

    /**
     * This method gets the account that has posted the post
     * @return the account that has posted the post
     */
    public Account getPoster(){
        return poster;
    }

    /**
     * Deletes posts
     */
    protected abstract void delete();

    /**
     * Resets the static counter shared by all posts
     */
    static public void resetCounters(){
        currentId=0;
    } 
    /**
     * This method checks if the post message is valid
     * @param message the message being checked
     * @return true if it valid
     * @throws InvalidPostException when trying to create an invalid post
     */
    protected boolean isValidMessage(String message) throws InvalidPostException{
        // This if statement checks if the message is empty or contains more than 100 characters, if it does then the exception is thrown
		if(message.equals("") || message.length() > 100){
			throw new InvalidPostException("Message must not be empty and be shorter than 100 characters");
		}
        return true;
    }

    /**
     * This method checks if a post is actionable
     * @param post the post being checked
     * @return true if actionable
     * @throws NotActionablePostException when trying to act upon an non-actionable post
     */
    protected boolean isActionable(Post post) throws NotActionablePostException{
        if(!(post instanceof ActionablePost)){
			throw new NotActionablePostException("Post cannot be acted upon");
		}
        return true;
    }
    
}
