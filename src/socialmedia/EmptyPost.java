package socialmedia;

import java.io.Serializable;

public class EmptyPost implements Serializable{
    //TODO
    protected String message;

    public EmptyPost(){
        this.message = "The original content was removed from the system and is no longer available.";
    }
    
    public String getMessaage() {
        return message;
    }
}
