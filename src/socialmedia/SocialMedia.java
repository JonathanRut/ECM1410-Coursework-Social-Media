package socialmedia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// A new variable is created for asserting the account was created
		int originalNumberOfAccounts = getNumberOfAccounts();

		// A pre-condition is used here to check if the handle is legal
		isLegalHandle(handle);

		// A new account object is created and instantiated
		Account newAccount = new Account(handle);

		// The id is retrieved from the account and used along with the account handle to add the account to the the hashmaps
		int id = newAccount.getId();
		accountsByHandle.put(handle, newAccount);
		accountsById.put(id, newAccount);

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfAccounts + 1 == getNumberOfAccounts()) : "Account not created successfully";

		// The id of new account is returned
		return id;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		// A new variable is created for asserting the account was created
		int originalNumberOfAccounts = getNumberOfAccounts();

		// A pre-condition is used here to check if the handle is legal
		isLegalHandle(handle);

		// A new account is made with the given handle and description
		Account newAccount = new Account(handle,description);

		// The id is retrieved from the account and used along with the account handle to add the account to the the hashmaps
		int id = newAccount.getId();
		accountsByHandle.put(handle, newAccount);
		accountsById.put(id, newAccount);

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfAccounts + 1 == getNumberOfAccounts() && description == newAccount.getDescription()) : "Account not created successfully";

		// The id of this new account is returned
		return id;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		// A new variable is created for asserting the account was removed
		int originalNumberOfAccounts = getNumberOfAccounts();

		// A pre-condition is used here to check if the account id is recognised
		isRecognisedAccountID(id);

		// The account to be deleted is retrieved and removed from the hashmaps using it's id and handle
		Account deleteAccount = accountsById.get(id);
		accountsById.remove(id);
		String handle = deleteAccount.getHandle();
		accountsByHandle.remove(handle);

		// The posts that the account has posted are iterated through and removed from the hashmap containing posts in the system
		ArrayList<Post> deletePosts = deleteAccount.getPosts();
		for(Post post : deletePosts){
			// This if statement looks for instances of ActionablePost in post if it is Actionable then it's endorsements are also removed from the Post hashmap
            if(post instanceof ActionablePost){
                ArrayList<Endorsement> endorsements = ((ActionablePost)post).getEndorsements();
                for(Endorsement endorsement : endorsements){
                    posts.remove(endorsement.getId());
                }
            }
			// The posts gets removed from the hashmap
            posts.remove(post.getId());
        }

		// Finally the account is deleted
		deleteAccount.delete();

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfAccounts - 1 == getNumberOfAccounts()) : "Account not deleted successfully";
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// A new variable is created for asserting the account was removed
		int originalNumberOfAccounts = getNumberOfAccounts();

		// A pre-condition is used here to check if the account handle is recognised
		isRecognisedHandle(handle);

		// The account to be deleted is retrieved and removed from the hashmaps using it's id and handle
		Account deleteAccount = accountsByHandle.get(handle);
		accountsByHandle.remove(handle);
		int id = deleteAccount.getId();
		accountsById.remove(id);

		// The posts that the account has posted are iterated through and removed from the hashmap containing posts in the system
		ArrayList<Post> deletePosts = deleteAccount.getPosts();
		for(Post post : deletePosts){
			// This if statement looks for instances of ActionablePost in post if it is Actionable then it's endorsements are also removed from the Post hashmap
            if(post instanceof ActionablePost){
                ArrayList<Endorsement> endorsements = ((ActionablePost)post).getEndorsements();
                for(Endorsement endorsement : endorsements){
                    posts.remove(endorsement.getId());
                }
            }
			// The posts gets removed from the hashmap
            posts.remove(post.getId());
        }

		// Finally the account is deleted
		deleteAccount.delete();
		
		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfAccounts - 1 == getNumberOfAccounts()) : "Account not deleted successfully";
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, 
			IllegalHandleException, InvalidHandleException{

		// Two pre-conditions are used here to check if the old handle is recognised and if the new handle is legal
		isRecognisedHandle(oldHandle);
		isLegalHandle(newHandle);
		
		// The account to be updated is retrieved from the hashmap
		Account account = accountsByHandle.get(oldHandle);

		// The handle of the account is updated
		account.setHandle(newHandle);

		// The old key value pair in the hashmap of accounts by handle is removed and updated with this new handle
		accountsByHandle.remove(oldHandle);
		accountsByHandle.put(newHandle, account);

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (oldHandle != account.getHandle()) : "Handle not changed successfully";
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {

		// The pre-condition checks here if the handle of the account to change is recognised
		isRecognisedHandle(handle);
		
		// The account to be updated is retrieved and its new description is set
		Account account = accountsByHandle.get(handle);
		account.setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {

		// The pre-condition checks here if the handle of the account to be shown is recognised
		isRecognisedHandle(handle);

		// The account related to the handle is found
		Account account = accountsByHandle.get(handle);

		// The information about the account is returned
		return account.toString();
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {

		// A new variable is created for asserting the post was created
		int originalNumberOfOriginalPosts = getTotalOriginalPosts();

		// The pre-condition checks if the handle of the account making the post is recognised in the system
		isRecognisedHandle(handle);

		// The account posting the post is retrieved from the hashmap
		Account poster = accountsByHandle.get(handle);

		// The new post is created with the poster account and message of the post
		OriginalPost newPost = new OriginalPost(poster, message);

		// The id of the post is retrieved and the post is added to the hashmaps of posts
		int id = newPost.getId();
		posts.put(id, newPost);

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfOriginalPosts + 1 == getTotalOriginalPosts()) : "Original post not created successfully";

		// The id of the new post is returned
		return id;
	}

	@Override
	public int endorsePost(String handle, int id)	throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException {
		// A new variable is created for asserting the post was endorsed
		int originalNumberOfEndorsements = getTotalEndorsmentPosts();

		// The two pre-condition checks if the handle or the post id is recognised in the system
		isRecognisedHandle(handle);
		isRecognisedPostID(id);

		// The post getting endorsed is retrieved an then checked to see if it is an endorsement
		// If it is an endorsement then the exception is thrown
		Post post = posts.get(id);

		// The account posting the endorsement is retrieved
		Account account = accountsByHandle.get(handle);

		// The endorsement is created with the account posting it and the post being endorsed
		Endorsement endorsement = new Endorsement(account, post);

		// The id of this endorsement is retrieved and the endorsement is added to the hashmaps of posts
		int newId = endorsement.getId();
		posts.put(newId, endorsement);

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfEndorsements + 1 == getTotalEndorsmentPosts()) : "Endorsement not created successfully";

		// The id of the new endorsement is returned
		return newId;
	}

	

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// A new variable is created for asserting the comment was created
		int originalNumberOfComments = getTotalCommentPosts();

		// The two pre-condition checks if the handle or the post id is recognised in the system
		isRecognisedHandle(handle);
		isRecognisedPostID(id);

		// The post being commented on and account posting is retrieved
		Post post = posts.get(id);
		Account account = accountsByHandle.get(handle);

		// The comment is created with the poster the post being commented and the message of the post
		Comment comment = new Comment(account, post, message);

		// The post id is retrieved then the post is added to the hashmap of posts
		int newId = comment.getId();
		posts.put(newId, comment);

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfComments + 1 == getTotalCommentPosts());

		// The id of the new post is returned
		return newId;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// A new variable is created for asserting the post was deleted
		int originalNumberOfPosts = posts.size();

		// The pre-condition checks if the id of post being deleted is recognised in the system
		isRecognisedPostID(id);
		
		// The post being deleted is retrieved
		Post delPost = posts.get(id);
		// If the post is an actionable post then a for loop runs removing all of its endorsements from the system
		if (delPost instanceof ActionablePost){
			for(Endorsement endorsement : ((ActionablePost)delPost).getEndorsements()){
				int endorseId = endorsement.getId();
				posts.remove(endorseId);
			}
		}

		// The post is removed from the system and deleted
		posts.remove(id);
		delPost.delete();
		
		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (originalNumberOfPosts - 1 == posts.size()) : "Post not deleted successfully";
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// The pre-condition checks if the id of the post being shown is recognised in the system
		isRecognisedPostID(id);

		// The IndividualPost related to the id is found
		Post post = posts.get(id);

		// The information about the IndividualPost is returned
		return post.toString();
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {

		// The pre-condition checks of the id of the posts children being shown is recognised in the system
		isRecognisedPostID(id);

		// The post is retrieved from the system
		Post post = posts.get(id);

		// A method from Post should generate a string builder of its details and its children
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
		// The number of original posts is retrieved from the Post class and then returned
		int numberOfPosts = OriginalPost.numberOriginalPosts;
		return numberOfPosts;
	}

	@Override
	public int getTotalEndorsmentPosts(){
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
			if(!(posts.get(id) instanceof ActionablePost)){
				continue;
			}
			ActionablePost currentPost = (ActionablePost)posts.get(id);
			if(currentPost.getNumberOfEndorsements() >= maxEndorsements){
				// If a new max number of endorsements is found the number is updated and the most endorsed id is updated
				maxEndorsements = currentPost.getNumberOfEndorsements();
				mostEndorsedId = id;
			}
		}

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (mostEndorsedId > 0) : "Most endorsed post id not found successfully";

		// Finally the id of the post is the most endorsements is returned
		return mostEndorsedId;
	}

	@Override
	public int getMostEndorsedAccount() {
		// The set of account id is retrieved from the accounts by id hashmap
		Set<Integer> ids = accountsById.keySet();

		// These variables keep track of the most endorsed id and the max number of endorsements
		int mostEndorsedId = -1;
		int maxEndorsements = 0;

		// This for loop iterates through the account ids in the system
		for(int id : ids){
			// The ith account is retrieved and then its endorsements is compared to the max number of endorsements
			Account currentAccount = accountsById.get(id);
			if(currentAccount.getEndorsements() >= maxEndorsements){
				// If a new max number of endorsements is found the number is updated and the most endorsed id is updated
				maxEndorsements = currentAccount.getEndorsements();
				mostEndorsedId = id;
			}
		}

		// Assertion checks that the post condition is met and if it is not met then it throws an exception
		assert (mostEndorsedId > 0) : "Most endorsed account id not found successfully";

		// Finally the id of the most endorsed account is returned
		return mostEndorsedId;
	}

	@Override
	public void erasePlatform() {
		// The HashMaps of post and accounts are reset to empty
		posts.clear();
		accountsById.clear();
		accountsByHandle.clear();
		// The counters for accounts, posts, original posts, comments and endorsements are all reset to the original values
		Account.resetCounters();
		Post.resetCounters();
		OriginalPost.resetCounters();
		Comment.resetCounters();
		Endorsement.resetCounters();
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// The current id of accounts being created and the number of accounts is retrieved
		int accountCurrentID = Account.currentId;
		int accountNumber = Account.numberAccounts;
		// The current id of posts being created and the number of original posts, comments and endorsements are retrieved
		int postCurrentID = Post.currentId;
		int originalPostNumber = OriginalPost.numberOriginalPosts;
		int commentPostNumber = Comment.numberComments;
		int endorsePostNumber = Endorsement.numberEndorsements;

		// An ObjectOutputStream is created for the desired file
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			// First 2 lines contain the counter information for accounts
			oos.writeObject(accountCurrentID);
			oos.writeObject(accountNumber);

			// The next 4 lines contain the counter information for posts
			oos.writeObject(postCurrentID);
			oos.writeObject(originalPostNumber);
			oos.writeObject(commentPostNumber);
			oos.writeObject(endorsePostNumber);

			// The final 3 lines contain the 3 HashMaps in the system
			oos.writeObject(posts);
			oos.writeObject(accountsById);
			oos.writeObject(accountsByHandle);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {

		// An ObjectInputStream is created for the desired file
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
			// The first 2 lines are read and casted to integers then they are used to set the account counters 
			Account.currentId = (int)in.readObject();
			Account.numberAccounts = (int)in.readObject();

			// The next 4 lines are read and casted to integers then they are used to set the post counters 
			Post.currentId = (int)in.readObject();
			OriginalPost.numberOriginalPosts = (int)in.readObject();
			Comment.numberComments = (int)in.readObject();
			Endorsement.numberEndorsements = (int)in.readObject();

			// The final 3 lines are read and casted to HashMaps and stores in the social media's post and account hashmaps
			posts = (HashMap<Integer, Post>)in.readObject();
			accountsById = (HashMap<Integer,Account>)in.readObject();
			accountsByHandle = (HashMap<String, Account>)in.readObject();
		}
	}

	/**
	 * This method checks if the handle is legal
	 * @param handle the handle being checked to see if it is legal
	 * @return true if legal
	 * @throws IllegalHandleException when trying to assign a handle that is already being used to an account 
	 */
	private boolean isLegalHandle(String handle) throws IllegalHandleException{
		// This if statement checks if the handle already exists in social media an throws the exception if it does
		if(accountsByHandle.get(handle) != null){
			throw new IllegalHandleException("Handle already exists in the system");
		}
		return true;
	}
	/**
	 * This method checks if the PostID is in the system
	 * @param id the PostID being checked to see if it is in the system
	 * @return true if it is in the system
	 * @throws PostIDNotRecognisedException when trying to use a PostID that is not in the system
	 */
	private boolean isRecognisedPostID(int id) throws PostIDNotRecognisedException{
		// This if statement checks if there is a post matching to the id in the system, if not the the exception is thrown
		if(posts.get(id) == null){
			throw new PostIDNotRecognisedException("No Post in the system with given ID");
		}
		return true;
	}
	/**
	 * This method checks if the handle is in the system
	 * @param handle the handle being checked to see if it system
	 * @return true if it is in the system
	 * @throws HandleNotRecognisedException when trying to use a handle that is not in the system
	 */
	private boolean isRecognisedHandle(String handle) throws HandleNotRecognisedException{
		// This if statement checks if there is an account with the given id in the system if there isn't the exception is thrown
		if(accountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException("Account with given handle does not exist in the system");
		}
		return true;
	}
	/**
	 * This method checks if the AccountID is in the system
	 * @param id the AccountID being checked to see if it is in the system
	 * @return true if it is in the system
	 * @throws AccountIDNotRecognisedException when trying to use an AccountID that is not in the system
	 */
	private boolean isRecognisedAccountID(int id) throws AccountIDNotRecognisedException{
		// This if statement checks if there is an account with the given id in the system if there isn't the exception is thrown
		if(accountsById.get(id) == null){
			throw new AccountIDNotRecognisedException("Account with given id does not exist in the system");
		}
		return true;
	}

}
