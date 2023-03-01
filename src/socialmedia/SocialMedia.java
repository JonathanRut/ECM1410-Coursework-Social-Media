package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * 
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	private HashMap<Integer, Post> Posts = new HashMap<Integer, Post>();
	private HashMap<String, Account> AccountsByHandle = new HashMap<String, Account>();
	private HashMap<Integer, Account> AccountsByID = new HashMap<Integer, Account>();

	static public void main(String[] args){
		HashMap<Integer, Post> Posts = new HashMap<Integer, Post>();
		System.out.println(Posts.get(2));
		Account account = new Account("Daniel");
		Post post = new Post(account, "2");
		System.out.println(post instanceof Endorsement);
	}

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		if(AccountsByHandle.get(handle) != null){
			throw new IllegalHandleException();
		}
		if(handle.equals("") || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}

		Account newAccount = new Account(handle);
		int id = newAccount.getId();
		AccountsByHandle.put(handle, newAccount);
		AccountsByID.put(id, newAccount);
		
		return id;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if(AccountsByHandle.get(handle) != null){
			throw new IllegalHandleException();
		}
		if(handle.equals("") || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}

		Account newAccount = new Account(handle,description);
		int id = newAccount.getId();
		AccountsByHandle.put(handle, newAccount);
		AccountsByID.put(id, newAccount);
		
		return id;
	}

	/**
	 * When an account is deleted the posts comments and endorsements related to the account are also deleted
	 * 
	 * When orignal post is deleted endorsements of the post should be deleted too
	 * comments on the original post remain but with no link to the original post
	 * 
	 * When endorsements are deleted they are just deleted ignore endorsements and comments here
	 * When comments are deleted the same as orignal post
	 */
	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		if(AccountsByID.get(id) == null){
			throw new AccountIDNotRecognisedException();
		}
		Account deleteAccount = AccountsByID.get(id);
		AccountsByID.remove(id);
		String handle = deleteAccount.getHandle();
		AccountsByHandle.remove(handle);

		ArrayList<Post> deletePosts = deleteAccount.getPosts();
		for(Post post : deletePosts){
			int postId = post.getId();
			Posts.remove(postId);
		}	

		deleteAccount.delete();
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		if(AccountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}
		Account deleteAccount = AccountsByHandle.get(handle);
		AccountsByHandle.remove(handle);
		int id = deleteAccount.getId();
		AccountsByID.remove(id);

		ArrayList<Post> deletePosts = deleteAccount.getPosts();
		for(Post post : deletePosts){
			int postId = post.getId();
			Posts.remove(postId);
		}	
		
		deleteAccount.delete();
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		if(AccountsByHandle.get(oldHandle) == null){
			throw new HandleNotRecognisedException();
		}
		if(AccountsByHandle.get(newHandle) != null){
			throw new IllegalHandleException();
		}
		if(newHandle.equals("") || newHandle.length() > 30 || newHandle.contains(" ")){
			throw new InvalidHandleException();
		}

		Account account = AccountsByHandle.get(oldHandle);
		account.setHandle(newHandle);
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		if(AccountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}

		Account account = AccountsByHandle.get(handle);
		account.setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		if(AccountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}
		if(message.equals("") || message.length() > 100){
			throw new InvalidPostException();
		}
		Account poster = AccountsByHandle.get(handle);
		Post newPost = new Post(poster, message);
		int id = newPost.getId();
		Posts.put(id, newPost);
		return id;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		if(AccountsByHandle.get(handle) == null){
			throw new HandleNotRecognisedException();
		}
		if(Posts.get(id) == null){
			throw new PostIDNotRecognisedException();
		}
		
		Post post = Posts.get(id);

		if(post instanceof Endorsement){
			throw new NotActionablePostException();
		}

		Account account = AccountsByHandle.get(handle);
		Endorsement endorsement = new Endorsement(account, post);
		int newId = endorsement.getId();
		Posts.put(newId, endorsement);
		return newId;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {		
		if(AccountsByHandle.get(handle)==null){
			throw new HandleNotRecognisedException();
		}

		if(Posts.get(id)==null){
			throw new PostIDNotRecognisedException();
		}

		Post post = Posts.get(id);

		if(post instanceof Endorsement){
			throw new NotActionablePostException();
		}

		if(message.equals("") || message.length() > 100){
			throw new InvalidPostException();
		}
		
		Account account = AccountsByHandle.get(handle);
		Comment comment = new Comment(account, post, message);
		int newId = comment.getId();
		Posts.put(newId, comment);
		return newId;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		if(Posts.get(id)==null){
			throw new PostIDNotRecognisedException();
		}
		
		Post deletePost = Posts.get(id);
		Posts.remove(id);
		deletePost.delete();
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		int numberOfAccounts = Account.numberAccounts;
		return numberOfAccounts;
	}

	@Override
	public int getTotalOriginalPosts() {
		int numberOfPosts = Post.NUMBER_POSTS;
		return numberOfPosts;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		int numberOfEndorsements = Endorsement.NUMBER_POSTS;
		return numberOfEndorsements;
	}

	@Override
	public int getTotalCommentPosts() {
		int numberOfComments = Comment.NUMBER_POSTS;
		return numberOfComments;
	}

	@Override
	public int getMostEndorsedPost() {
		Set<Integer> ids = Posts.keySet();
		int mostEndorsedId = -1;
		int maxEndorsements = 0;
		for(int id: ids){
			Post currentPost = Posts.get(id);
			if(currentPost.getNumberOfEndorsements() >= maxEndorsements){
				maxEndorsements = currentPost.getNumberOfEndorsements();
				mostEndorsedId = id;
			}
		}
		return mostEndorsedId;
	}

	@Override
	public int getMostEndorsedAccount() {
		Set<Integer> ids = AccountsByID.keySet();
		int mostEndorsedId = -1;
		int maxEndorsements = 0;
		for(int id : ids){
			Account currentAccount = AccountsByID.get(id);
			if(currentAccount.getEndorsements() >= maxEndorsements){
				maxEndorsements = currentAccount.getEndorsements();
				mostEndorsedId = id;
			}
		}
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
