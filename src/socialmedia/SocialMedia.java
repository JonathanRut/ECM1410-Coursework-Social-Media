package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The social media class provides an interface to create, modify, read and delete posts or accounts
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	/**
	 * A {@link HashMap} is used here to store key value pairs with the keys of type {@link Integer} and values of type {@link Post}
	 * This provides a way of getting Posts based of IDs
	 */
	public static HashMap<Integer, Post> posts = new HashMap<Integer, Post>();
	/**
	 * A {@link HashMap} is used here to store key value pairs with the keys of type {@link String} and values of type {@link Account}
	 * This provides a way of getting Accounts based of their handles
	 */
	public static HashMap<String, Account> accountsByHandle = new HashMap<String, Account>();
	/**
	 * A {@link HashMap} is used here to store key value pairs with the keys of type {@link Integer} and values of type {@link Account}
	 * This provides a way of getting Accounts based of their ids
	 */
	public static HashMap<Integer, Account> accountsById = new HashMap<Integer, Account>();

	static public void main(String[] args) throws Exception{
		SocialMedia socialMedia = new SocialMedia();
		// Createing accounts
		socialMedia.createAccount("user1");
		socialMedia.createAccount("user2");
		socialMedia.createAccount("user3");
		socialMedia.createAccount("user5");

		// Createing posts
		socialMedia.createPost("user1", "j");
		socialMedia.createPost("user1", "I like examples.");
		socialMedia.endorsePost("user2", 1);

		// Creating comments
		socialMedia.commentPost("user2", 1, "No more than me...");
		socialMedia.commentPost("user3", 1, "Can't you do better than this?");
		socialMedia.commentPost("user1", 3, "I can prove it!");
		socialMedia.commentPost("user2", 5, "prove it");
		socialMedia.commentPost("user5", 1, "where is the example?");
		socialMedia.endorsePost("user1", 4);
		socialMedia.endorsePost("user2", 4);
		socialMedia.commentPost("user1", 7, "This is the example!");
		socialMedia.endorsePost("user5", 4);
		socialMedia.endorsePost("user2", 4);

		System.out.println(socialMedia.showPostChildrenDetails(5).toString());
	}

	/**
	 * This methods is used to create an account with a given handle
	 * @param handle The handle of the account to be created
	 * @throws IllegalHandleException if the handle already exists in the platform.
	 * @throws InvalidHandleException if the handle is empty or has more than 30 characters or contains whitespaces
	 * @return The id of the new account
	 */
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		
		// A new account object is created and instatiated
		Account newAccount = new Account(handle);
		// The id is retrieved from the account and used along with the account handle to add the account to the the hashmaps
		int id = newAccount.getId();
		accountsByHandle.put(handle, newAccount);
		accountsById.put(id, newAccount);
		// The id of new account is returned
		return id;
	}

	/**
	 * This method creates and account with a given handle and description
	 * @param handle The handle of the account to be created
	 * @param description The description of the account to be created
	 * @throws IllegalHandleException if the handle already exists in the platform.
	 * @throws InvalidHandleException if the handle is empty or has more than 30 characters or contains whitespaces
	 * @return The id of the new account
	 */
	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		// This if statement checks if the handle already exists in social media an throws the exception if it does
		if(accountsByHandle.get(handle) != null){
			throw new IllegalHandleException();
		}
		// This if statement checks if the handle is empty, has more than 30 characters or contains whitespaces and throws the exception if it does
		if(handle.equals("") || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}

		// A new account is made with the given handle and description
		Account newAccount = new Account(handle,description);
		// The id is retrieved from the account and used along with the account handle to add the account to the the hashmaps
		int id = newAccount.getId();
		accountsByHandle.put(handle, newAccount);
		accountsById.put(id, newAccount);
		// The id of this new account is returned
		return id;
	}

	/**
	 * This methods deletes an account based of a given id
	 * @param id The id of the account to be deleted
	 * @throws AccountIDNotRecognisedException if the account does not match any id in the system
	 */
	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		// This if statement checks if there is an account with the given id in the system if there isn't the exception is thrown
		if(accountsById.get(id) == null){
			throw new AccountIDNotRecognisedException();
		}

		// The account to be deleted is retrieved and removed from the hashmaps using it's id and handle
		Account deleteAccount = accountsById.get(id);
		accountsById.remove(id);
		String handle = deleteAccount.getHandle();
		accountsByHandle.remove(handle);

		// The posts that the account has posted are iterated through and removed from the hashmap containing posts in the system
		ArrayList<Post> deletePosts = deleteAccount.getPosts();
		for(Post post : deletePosts){
			int postId = post.getId();
			posts.remove(postId);
		}	
		// Finally the account is deleted
		deleteAccount.delete();
	}

	/**
	 * This method deletes an account based of a given handle
	 * @param handle The handle of the account to be deleted
	 * @throws HandleNotRecognisedException if the handle doesn't match to any account in the system
	 */
	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// This if statements checks if there is an account with the given handle in the system if there isn't the exception is thrown
		if(accountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}
		// The account to be deleted is retrieved and removed from the hashmaps using it's id and handle
		Account deleteAccount = accountsByHandle.get(handle);
		accountsByHandle.remove(handle);
		int id = deleteAccount.getId();
		accountsById.remove(id);

		// The posts that the account has posted are iterated through and removed from the hashmap containing posts in the system
		ArrayList<Post> deletePosts = deleteAccount.getPosts();
		for(Post post : deletePosts){
			int postId = post.getId();
			posts.remove(postId);
		}	
		// Finally the account is deleted
		deleteAccount.delete();
	}

	/**
	 * This method is used to change the handle of an account
	 * @param oldHandle The old hanlde of the account to be edited
	 * @param newHandle	The new handle of the account to be edited
	 * @throws HandleNotRecognisedException if the old handle doesn't match to any account in the system
	 * @throws IllegalHandleException if the new handle already is used for another account in the system
	 * @throws InvalidHandleException if the new handle is empty or has more than 30 characters or contains whitespaces
	 */
	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		// This if statement checks if there is an account to match to the old handle, if there isn't the exception is thrown
		if(accountsByHandle.get(oldHandle) == null){
			throw new HandleNotRecognisedException();
		}
		// This if statement checks if there is an account in the system already with the new handle, if there is the exception is thrown
		if(accountsByHandle.get(newHandle) != null){
			throw new IllegalHandleException();
		}
		// This if statement check for is the new handle is empty or has more than 30 characters or contains whitespaces, if it is then the exception is thrown
		if(newHandle.equals("") || newHandle.length() > 30 || newHandle.contains(" ")){
			throw new InvalidHandleException();
		}

		// The account to be updated is retrieved from the hashmap
		Account account = accountsByHandle.get(oldHandle);
		// The handle of the account is updated
		account.setHandle(newHandle);
		// The old key value pair in the hashmap of accounts by handle is removed and updated with this new handle
		accountsByHandle.remove(oldHandle);
		accountsByHandle.put(newHandle, account);
	}

	/**
	 * This method updates the description of an account with a given handle
	 * @param handle The handle of the account whose description is to be updated
	 * @param description The new description to be set to the account
	 * @throws HandleNotRecognisedException if the handle doesn't match to any account in the system
	 */
	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		// This if statement checks if there is an account to match to the handle, if there isn't the exception is thrown
		if(accountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}

		// The account to be updated is retrieved and its new desciprion is set
		Account account = accountsByHandle.get(handle);
		account.setDescription(description);
	}
	/**
	 * This method shows an account and returns a string of information about the account
	 * @param handle the handle of the account being shown
	 * @throws HandleNotRecognisedException if the handle doesn't match to any account in the system
	 * @return A string of information about the account
	 */
	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// This if statement checks if there is an account to match to the handle, if there isn't the exception is thrown
		if(accountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}
		// The account related to the handle is found
		Account account = accountsByHandle.get(handle);
		// The information about the account is returned
		return account.toString();
	}

	/**
	 * This method creates a post with a given message from a account with a given handle
	 * @param handle The handle of the account creating the post
	 * @param message The message of the post being created
	 * @throws HandleNotRecognisedException if the handle doesn't match to an account in the system
	 * @throws InvalidPostException if the post is empty or more than 100 character
	 * @return the id of the post created
	 */
	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// This if statement checks if there is a account matching to the handle in the system, if not the the exception is thrown
		if(accountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}
		// This if statement checks if the message is empty or contains more than 100 characters, if it does then the exception is thrown
		if(message.equals("") || message.length() > 100){
			throw new InvalidPostException();
		}
		// The account posting the post is retrived from the hashmap
		Account poster = accountsByHandle.get(handle);
		// The new post is created with the poster account and message of the post
		Post newPost = new Post(poster, message);
		// The id of the post is retrieved and the post is added to the hashmaps of posts
		int id = newPost.getId();
		posts.put(id, newPost);
		// The id of the new post is returned
		return id;
	}

	/**
	 * This method created a endorsement of a post with the given id
	 * @param handle The handle of the account posting the endorsement
	 * @param id The id of the post being endorsed
	 * @throws HandleNotRecognisedException if the handle doesn't match to an account in the system
	 * @throws PostIDNotRecognisedException if the id doesn't match to an post in the system
	 * @throws NotActionablePostException if the id refers to an endorsement post
	 * @return the id of the new endorsement
	 */
	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// This if statement checks if there is a account matching to the handle in the system, if not the the exception is thrown
		if(accountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}
		// This if statement checks if there is a post matching to the id in the system, if not the the exception is thrown
		if(posts.get(id) == null){
			throw new PostIDNotRecognisedException();
		}
		
		// The post getting endorsed is retrieved an then checked to see if it is an endorsement
		// If it is an endorsement then the exception is thrown
		Post post = posts.get(id);
		if(post instanceof Endorsement){
			throw new NotActionablePostException();
		}

		// The account posting the endorsement is retrieved
		Account account = accountsByHandle.get(handle);
		// The endorsement is created with the account posting it and the post being endorsed
		Endorsement endorsement = new Endorsement(account, post);
		// The id of this endorsement is retrieved and the endorsement is added to the hashmaps of posts
		int newId = endorsement.getId();
		posts.put(newId, endorsement);
		// The id of the new endorsement is returned
		return newId;
	}

	/**
	 * This method is used to create a comment post
	 * @param handle The hanlde of the account posting the comment
	 * @param id The id of the post being commented on
	 * @param message The message of the comment
	 * @throws HandleNotRecognisedException if the handle doesn't match to an account in the system
	 * @throws PostIDNotRecognisedException if the id doesn't match to a post in the system
	 * @throws NotActionablePostException if the id refers to an endorsement
	 * @throws InvalidPostException if the comment message is empty or longer than 100 characters
	 */
	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {	
		// This if statement checks for if an account matches to the handle if it doesn't then the exception is thrown	
		if(accountsByHandle.get(handle)==null){
			throw new HandleNotRecognisedException();
		}
		// This if statement checks for if a post matches to the id if it doesn't then the exception is thrown	
		if(posts.get(id)==null){
			throw new PostIDNotRecognisedException();
		}

		// The post being commented on is checked to see if it is an endorsement if it is then the exception is thrown
		Post post = posts.get(id);
		if(post instanceof Endorsement){
			throw new NotActionablePostException();
		}

		// This if statement checks if the message is empty or has more than 100 characters if it does then the exception is thrown
		if(message.equals("") || message.length() > 100){
			throw new InvalidPostException();
		}
		
		// The account posting the comment is retrieved
		Account account = accountsByHandle.get(handle);
		// The comment is created with the poster the post being commented and the message of the post
		Comment comment = new Comment(account, post, message);
		// The post id is retrieved then the post is added to the hashmap of posts
		int newId = comment.getId();
		posts.put(newId, comment);
		// The id of the new post is returned
		return newId;
	}

	/**
	 * This methods is used to delete posts
	 * @param id The id of the post to be deleted
	 * @throws PostIDNotRecognisedException if the id doesn't match to a post in the system
	 */
	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// This if statement checks for if the id matches to a post in the hashmap
		if(posts.get(id)==null){
			throw new PostIDNotRecognisedException();
		}
		
		// The post to be deleted is retrieved
		Post deletePost = posts.get(id);
		// The post is removed from the hashmap of posts then deleted
		posts.remove(id);
		deletePost.delete();
	}
	/**
	 * @param id the id of the post that the information is about
	 * @throws PostIDNotRecognisedException if the id doesn't match to a post in the system
	 * @return a string of information about the account
	 */
	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// This if statement checks for if the id matches to a post in the hashmap
		if(posts.get(id)==null){
			throw new PostIDNotRecognisedException();
		}
		// The IndividualPost related to the handle is found
		Post post = posts.get(id);
		// The information about the IndividualPost is returned
		return post.toString();
	}

	/**
	 * This methods makes a string builder of a post details and the childrens detials for a given post id
	 * @param id The post for the string builder to be generated from
	 * @throws PostIDNotRecognisedException if the id doesn't match to a post in the system
	 * @throws NotActionablePostException if the id refers to an endorsement
	 * @return the string builder of the post and its children 
	 */
	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// This if statement checks for if the id matches to a post in the hashmap
		if(posts.get(id)==null){
			throw new PostIDNotRecognisedException();
		}

		// The post having the strinbg builder genereted on is checked to see if it is an endorsement if it is then the exception is thrown
		Post post = posts.get(id);
		if(post instanceof Endorsement){
			throw new NotActionablePostException();
		}

		// A method from Post should generate a string builder of its details and its childrens
		StringBuilder postChildrenDetails = post.showChildren(0);
		// The string builder is returned
		return postChildrenDetails;
	}

	/**
	 * This method get the total number of accounts on the social media
	 * @return the number of account on the social media
	 */
	@Override
	public int getNumberOfAccounts() {
		// The number of accounts is retrieved from the account class then returned
		int numberOfAccounts = Account.numberAccounts;
		return numberOfAccounts;
	}

	/**
	 * This method gets the total number of original posts on the social media
	 * @return the number of original posts on the social media
	 */
	@Override
	public int getTotalOriginalPosts() {
		// The number of orignal posts is retrieved from the Post class and then returned
		int numberOfPosts = Post.numberPosts;
		return numberOfPosts;
	}

	/**
	 * This method gets the total number of endorsement posts on the the social media
	 * @return the number of endorsement posts on the social media
	 */
	@Override
	public int getTotalEndorsmentPosts() {
		// The number of endorsements is retrieved from the Endorsement class and then returned
		int numberOfEndorsements = Endorsement.numberPosts;
		return numberOfEndorsements;
	}

	/**
	 * This method gets the total number of comments on the social media
	 * @return the number of comments on the socail media
	 */
	@Override
	public int getTotalCommentPosts() {
		// The number of comments is retrieved from the Comment class and then returned
		int numberOfComments = Comment.numberPosts;
		return numberOfComments;
	}

	/**
	 * This method gets the id of the most endorsed post in the system
	 * @return the id of the most endorsed post in the system
	 */
	@Override
	public int getMostEndorsedPost() {
		// The set of ids is retrieved from the hashmaps of posts
		Set<Integer> ids = posts.keySet();
		// These variables keep track of the most endorsed id the max number of endorsements
		int mostEndorsedId = -1;
		int maxEndorsements = 0;
		// This for loop iterates through the post ids in the system
		for(int id: ids){
			// The ith post is retrieved and then compared to the max number of endorsements
			Post currentPost = posts.get(id);
			if(currentPost.getNumberOfEndorsements() >= maxEndorsements){
				// If a new max number of endorsements is found the number is updated and the most endorsed id is updated
				maxEndorsements = currentPost.getNumberOfEndorsements();
				mostEndorsedId = id;
			}
		}
		// Finally the id of the post is the most endorsements is returned
		return mostEndorsedId;
	}

	/**
	 * This method gets the id of the account with the most endorsements on their posts
	 * @return the id of the most endorsed account
	 */
	@Override
	public int getMostEndorsedAccount() {
		// The set of account id is retrived from the accounts by id hashmap
		Set<Integer> ids = accountsById.keySet();
		// These variables keep track of the most endorsed id and the max number of endorsements
		int mostEndorsedId = -1;
		int maxEndorsements = 0;
		// This for loop iterates through the account ids in the system
		for(int id : ids){
			// The ith account is retrieved and then its endorsements is comapred to the max number of endorsements
			Account currentAccount = accountsById.get(id);
			if(currentAccount.getEndorsements() >= maxEndorsements){
				// If a new max number of endorsements is found the number is updated and the most endorsed id is updated
				maxEndorsements = currentAccount.getEndorsements();
				mostEndorsedId = id;
			}
		}
		// Finally the id of the most endorsed account is returned
		return mostEndorsedId;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
	}

}
