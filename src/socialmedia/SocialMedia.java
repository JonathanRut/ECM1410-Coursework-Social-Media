package socialmedia;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * 
 * 
 * @author Jonathan Rutland & Daniel Stirling Barros
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	private Dictionary<Integer, Post> Posts = new Hashtable<>();
	private Dictionary<String, Account> AccountsByHandle = new Hashtable<>();
	private Dictionary<Integer, Account> AccountsByID = new Hashtable<>();

	static public void main(String[] args){
		Dictionary<Integer, Post> Posts = new Hashtable<>();
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

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
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
