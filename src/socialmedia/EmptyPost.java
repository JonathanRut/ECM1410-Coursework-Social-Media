package socialmedia;

public class EmptyPost {
    /**
     * A protected variable {@link String} used to store the message for the post
     */
    protected String message;

    /**
     * This constructor creates an empty post
     */
    public EmptyPost(){
        this.message = "The original content was removed from the system and is no longer available.";
    }
    /**
     * gets the message of the post
     * @return the message of the post
     */
    public String getMessage() {
        return message;
    }
}
