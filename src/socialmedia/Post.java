package socialmedia;

public abstract class Post {
    /**
     * A protected constant {@link Integer} used to store the id of a post this is unique to the post
     */
    protected final int ID;

    /**
     * A protected variable {@link String} used to store the message for the post
     */
    protected String message;

    /**
     * A protected variable {@link Account} used to store the account who posted the post
     */
    protected Account poster;

    /**
     * A private variable {@link Integer} that stores the id of the next post to be created
     */
    private static int currentId=0;

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

    /**
     * This method gets the message and then returns the mesage of the post
     * @return the message of the message
     */
    public String getMessage(){
        return this.message;
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

    public abstract void delete();

    public void resetCounters(){
        currentId=0;
    } 
     
}
