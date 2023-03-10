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
	private HashMap<Integer, Post> posts = new HashMap<Integer, Post>();
	/**
	 * A {@link HashMap} is used here to store key value pairs with the keys of type {@link String} and values of type {@link Account}
	 * This provides a way of getting Accounts based of their handles
	 */
	private HashMap<String, Account> accountsByHandle = new HashMap<String, Account>();
	/**
	 * A {@link HashMap} is used here to store key value pairs with the keys of type {@link Integer} and values of type {@link Account}
	 * This provides a way of getting Accounts based of their ids
	 */
	private HashMap<Integer, Account> accountsById = new HashMap<Integer, Account>();

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

		/*
			4 accounts
		 	2 original posts
			5 endorements
			6 comments
		 */

		System.out.println(socialMedia.showPostChildrenDetails(1).toString());
		System.out.println(socialMedia.getNumberOfAccounts());
		System.out.println(socialMedia.getTotalOriginalPosts());
		System.out.println(socialMedia.getTotalEndorsmentPosts());
		System.out.println(socialMedia.getTotalCommentPosts());
	}

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		int originalNumberOfAccounts = getNumberOfAccounts();
		isLegalHandle(handle);
		// A new account object is created and instatiated
		Account newAccount = new Account(handle);
		// The id is retrieved from the account and used along with the account handle to add the account to the the hashmaps
		int id = newAccount.getId();
		accountsByHandle.put(handle, newAccount);
		accountsById.put(id, newAccount);

		assert (originalNumberOfAccounts + 1 == getNumberOfAccounts()) : "Account not created sucessfully";

		// The id of new account is returned
		return id;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		int originalNumberOfAccounts = getNumberOfAccounts();

		isLegalHandle(handle);
		// A new account is made with the given handle and description
		Account newAccount = new Account(handle,description);
		// The id is retrieved from the account and used along with the account handle to add the account to the the hashmaps
		int id = newAccount.getId();
		accountsByHandle.put(handle, newAccount);
		accountsById.put(id, newAccount);

		assert (originalNumberOfAccounts + 1 == getNumberOfAccounts() && description == newAccount.getDescription()) : "Account not created sucessfully";

		// The id of this new account is returned
		return id;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		int originalNumberOfAccounts = getNumberOfAccounts();
		isRecognisedAccountID(id);
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

		assert (originalNumberOfAccounts - 1 == getNumberOfAccounts()) : "Account not deleted sucessfully";
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		int originalNumberOfAccounts = getNumberOfAccounts();
		isRecognisedHandle(handle);
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
		assert (originalNumberOfAccounts - 1 == getNumberOfAccounts()) : "Account not deleted sucessfully";
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		isRecognisedHandle(oldHandle);
		isLegalHandle(newHandle);
		// The account to be updated is retrieved from the hashmap
		Account account = accountsByHandle.get(oldHandle);
		// The handle of the account is updated
		account.setHandle(newHandle);
		// The old key value pair in the hashmap of accounts by handle is removed and updated with this new handle
		accountsByHandle.remove(oldHandle);
		accountsByHandle.put(newHandle, account);

		assert (oldHandle != account.getHandle()) : "Handle not changed sucessfully";
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		isRecognisedHandle(handle);
		// The account to be updated is retrieved and its new desciprion is set
		Account account = accountsByHandle.get(handle);
		String oldDescription= account.getDescription();
		account.setDescription(description);

		assert (oldDescription != account.getDescription()) : "Description not changed sucessfully";
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		isRecognisedHandle(handle);
		// The account related to the handle is found
		Account account = accountsByHandle.get(handle);
		// The information about the account is returned
		return account.toString();
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		int originalNumberOfOriginalPosts = getTotalOriginalPosts();
		isRecognisedHandle(handle);
		// The account posting the post is retrived from the hashmap
		Account poster = accountsByHandle.get(handle);
		// The new post is created with the poster account and message of the post
		OriginalPost newPost = new OriginalPost(poster, message);
		// The id of the post is retrieved and the post is added to the hashmaps of posts
		int id = newPost.getId();
		posts.put(id, newPost);

		assert (originalNumberOfOriginalPosts + 1 == getTotalOriginalPosts()) : "Original post not created sucessfully";

		// The id of the new post is returned
		return id;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		int originalNumberOfEndorsements = getTotalEndorsmentPosts();
		isRecognisedHandle(handle);
		isRecognisedPostID(id);
		
		// The post getting endorsed is retrieved an then checked to see if it is an endorsement
		// If it is an endorsement then the exception is thrown
		Post post = posts.get(id);
		if(post instanceof Endorsement){
			throw new NotActionablePostException();
		}

		// The account posting the endorsement is retrieved
		Account account = accountsByHandle.get(handle);
		// The endorsement is created with the account posting it and the post being endorsed
		Endorsement endorsement = new Endorsement(account, (ActionablePost)post);
		// The id of this endorsement is retrieved and the endorsement is added to the hashmaps of posts
		int newId = endorsement.getId();
		posts.put(newId, endorsement);

		assert (originalNumberOfEndorsements + 1 == getTotalEndorsmentPosts()) : "Endorsement not created sucessfully";

		// The id of the new endorsement is returned
		return newId;
	}

	

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		int originalNumberOfComments = getTotalCommentPosts();
		isRecognisedHandle(handle);
		isRecognisedPostID(id);

		Post post = posts.get(id);
		// The account posting the comment is retrieved
		Account account = accountsByHandle.get(handle);
		// The comment is created with the poster the post being commented and the message of the post
		Comment comment = new Comment(account, post, message);
		// The post id is retrieved then the post is added to the hashmap of posts
		int newId = comment.getId();
		posts.put(newId, comment);

		assert (originalNumberOfComments + 1 == getTotalCommentPosts());
		// The id of the new post is returned
		return newId;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		int originalNumberOfPosts = posts.size();
		isRecognisedPostID(id);
		// The post to be deleted is retrieved
		Post deletePost = posts.get(id);
		// The post is removed from the hashmap of posts then deleted
		posts.remove(id);
		deletePost.delete();

		assert (originalNumberOfPosts - 1 == posts.size()) : "Post not deleted successfully";
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		isRecognisedPostID(id);
		// The IndividualPost related to the handle is found
		Post post = posts.get(id);
		// The information about the IndividualPost is returned
		return post.toString();
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		isRecognisedPostID(id);
		Post post = posts.get(id);
		// A method from Post should generate a string builder of its details and its childrens
		StringBuilder postChildrenDetails = ((ActionablePost)post).showChildren(0);
		// The string builder is returned
		return postChildrenDetails;
	}

	@Override
	public int getNumberOfAccounts() {
		// The number of accounts is retrieved from the account class then returned
		int numberOfAccounts = Account.numberAccounts;
		return numberOfAccounts;
	}

	@Override
	public int getTotalOriginalPosts() {
		// The number of orignal posts is retrieved from the Post class and then returned
		int numberOfPosts = OriginalPost.numberOriginalPosts;
		return numberOfPosts;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// The number of endorsements is retrieved from the Endorsement class and then returned
		int numberOfEndorsements = Endorsement.numberEndorsements;
		return numberOfEndorsements;
	}

	@Override
	public int getTotalCommentPosts() {
		// The number of comments is retrieved from the Comment class and then returned
		int numberOfComments = Comment.numberComments;
		return numberOfComments;
	}

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
			if(((ActionablePost)currentPost).getNumberOfEndorsements() >= maxEndorsements){
				// If a new max number of endorsements is found the number is updated and the most endorsed id is updated
				maxEndorsements = ((ActionablePost)currentPost).getNumberOfEndorsements();
				mostEndorsedId = id;
			}
		}

		assert (mostEndorsedId > 0) : "Most endorsed post id not found sucessfully";
		// Finally the id of the post is the most endorsements is returned
		return mostEndorsedId;
	}

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
		assert (mostEndorsedId > 0) : "Most endorsed account id not found sucessfully";
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

	
	private boolean isLegalHandle(String handle) throws IllegalHandleException{
		// This if statement checks if the handle already exists in social media an throws the exception if it does
		if(accountsByHandle.get(handle) != null){
			throw new IllegalHandleException("Handle already exists in the system");
		}
		return true;
	}

	private boolean isRecognisedPostID(int id) throws PostIDNotRecognisedException{
		// This if statement checks if there is a post matching to the id in the system, if not the the exception is thrown
		if(posts.get(id) == null){
			throw new PostIDNotRecognisedException("No Post in the system with given ID");
		}
		return true;
	}

	private boolean isRecognisedHandle(String handle) throws HandleNotRecognisedException{
		// This if statement checks if there is an account with the given id in the system if there isn't the exception is thrown
		if(accountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException("Account with given handle does not exist in the system");
		}
		return true;
	}

	private boolean isRecognisedAccountID(int id) throws AccountIDNotRecognisedException{
		// This if statement checks if there is an account with the given id in the system if there isn't the exception is thrown
		if(accountsById.get(id) == null){
			throw new AccountIDNotRecognisedException("Account with given id does not exist in the system");
		}
		return true;
	}

}
