package socialmedia;

import java.util.ArrayList;

/**
 * The account class is used to create objects that represent users that can post to the social media
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class Account {
    
    /**
     * A public {@link Integer} representing the number of accounts on the social media platform
     */
    public static int numberAccounts = 0;

    /**
     * A private {@link Integer} representing the current id for a new account
     */
    private static int currentId = 0;

    /**
     * A private {@link String} used to store the handle of an account this is unique to the account
     */
    private String handle;
    /**
     * A private constant {@link Integer} used to store the id of an account this is unique to the account
     */
    private final int ID;
    /**
     * A private {@link String} used to store the description of the account
     */
    private String description;
    /**
     * A private {@link Integer} used to store the total number of endorsements from the accounts posts
     */
    private int numberOfEndorsements = 0;
    /**
     * A private {@link ArrayList} storing elements of type {@link Post} that represents the posts the account has made
     */
    private ArrayList<Post> posts = new ArrayList<Post>();


    /**
     * This constructor makes an Account with a given handle
     * 
     * @param handle The handle which will be given to the account
     */
    public Account(String handle) throws IllegalHandleException, InvalidHandleException {
        if(SocialMedia.accountsByHandle.get(handle) !=null){
            throw new IllegalHandleException();
        }

        if(handle.equals("") || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}
        //TODO
       // The accounts handle, id and description are set
        this.handle = handle;
        this.ID = currentId;
        this.description = "";
        // Then the counters are incremented by 1
        currentId++;
        numberAccounts++;
        
    }

    /**
     * This constructor makes an Account with a given handle and description
     * 
     * @param handle The hadle which will be given to the account
     * @param description The description which will be given to the account
     */
    public Account(String handle, String description) throws IllegalHandleException, InvalidHandleException{
        if(SocialMedia.accountsByHandle.get(handle) !=null){
            throw new IllegalHandleException();
        }

        if(handle.equals("") || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}
        //TODO
        // The accounts handle, id and description are set
        this.handle = handle;
        this.ID = currentId;
        this.description = description;
        // Then the couters are incremented by 1
        currentId++;
        numberAccounts++;
    }

    /**
     * Gets the handle of an account
     * 
     * @return the handle of an account
     */
    public String getHandle(){
        return this.handle;
    }

    /**
     * Sets the handle of an account
     * 
     * @param handle the new handle of an account to be set
     */
    public void setHandle(String handle) throws IllegalHandleException, InvalidHandleException {
        //TODO
        // This if statement checks if there is an account in the system already with the new handle, if there is the exception is thrown
		if(SocialMedia.accountsByHandle.get(handle) != null){
			throw new IllegalHandleException();
		}
        // This if statement check for is the new handle is empty or has more than 30 characters or contains whitespaces, if it is then the exception is thrown
		if(handle.equals("") || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}
        this.handle = handle;
    }

    /**
     * Gets the id of an account
     * 
     * @return the id of an account
     */
    public int getId(){
        return this.ID;
    }

    /**
     * Gets the description of an account
     * 
     * @return the description of an account
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Sets the description of an account
     * 
     * @param description the new description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the number of endorsements an account has received on posts
     * 
     * @return the number of endorsements an account has received on posts
     */
    public int getEndorsements(){
        return this.numberOfEndorsements;
    }

    /**
     * Increments the number of endorsements an account has received on posts
     */
    public void incrementEndorsements(){
        this.numberOfEndorsements++;
    }

    /**
     * Decrementes the number of endorsements and account has receieved on posts
     */
    public void decrementEndorsements(){
        this.numberOfEndorsements--;
    }

    /**
     * Deletes an account by deleting its posts and decrementing the counter
     */
    public void delete() {
        // The for loops goes through the accounts posts and calls their delete method
        for(Post post : posts){
            post.delete();
        }
        // The number of accounts is decremented by 1
        numberAccounts--;
    }

    /**
     * Gets the list of posts that an account has posted
     * 
     * @return the posts an account has posted
     */
    public ArrayList<Post> getPosts(){
        return this.posts;
    }

    /**
     * Resets the counters for number of account and current id to their intital values
     */
    public void resetCounters(){
        numberAccounts = 0;
        currentId = 0;
    }

    /**
     * Adds a given post to an accounts list of posts
     * 
     * @param post the post to be added to an accounts list of posts
     */
    public void addPost(Post post){
        posts.add(post);
    }

    /** 
     * Removes a post from an accounts list of posts
     * 
     * @param post the post to be removed from the list of posts
     */
    public void removePost(Post post){
        // The index of the post to be removed is found using a for loop
        int index = -1;
        for(int i = 0; i < posts.size(); i++){
            if(posts.get(i) == post){
                index = i;
            }
        }
        // The post is removed using this ID
        posts.remove(index);
    }
    /**
     * This method returns a string of information about the account
     * @return the string of information about the account
     */
    @Override
    public String toString(){
        // A new StringBuilder is created and then information about the account is appended to it
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ID: ").append(ID).append("\n");
		stringBuilder.append("Handle: ").append(handle).append("\n");
		stringBuilder.append("Description: ").append(description).append("\n");
		stringBuilder.append("Post count: ").append(posts.size()).append("\n");
		stringBuilder.append("Endorse count: ").append(numberOfEndorsements).append("\n");
        // The information is returned as a string
        return stringBuilder.toString();
    }

    
}
