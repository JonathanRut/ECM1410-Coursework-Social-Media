package socialmedia;

import java.io.Serializable;

/**
 * The empty post class is used to create objects that represent user an empty post with a message
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class EmptyPost implements Serializable{

     /**
     * A protected attribute {@link String} that stores the message of the Post being created
     */
    protected String message;

    /**
     * This constructor creates an empty post
     */
    public EmptyPost(){
        this.message = "The original content was removed from the system and is no longer available.";
    }


    /**
     * Gets the message of the post
     * @return the message of the post
     */
    public String getMessage() {
        return message;
    }
}
