import java.io.File;
import java.io.IOException;

import socialmedia.AccountIDNotRecognisedException;
import socialmedia.HandleNotRecognisedException;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.InvalidPostException;
import socialmedia.NotActionablePostException;
import socialmedia.PostIDNotRecognisedException;
import socialmedia.SocialMedia;
import socialmedia.SocialMediaPlatform;

/*
 * To run with assertions run in this order
 * javac -d bin .\src\socialmedia\*.java
 * javac -cp bin -d bin .\src\SocialMediaPlatformTestApp.java
 * java -cp bin. -ea  SocialMediaPlatformTestApp
 */

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws Exception{
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Initial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Initial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Initial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Initial SocialMediaPlatform not empty as required.";

		Integer id;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		}


		// Testing typical case for create account
		try{
			platform.createAccount("ham");
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		// Testing erroneous case for create account for if handle contains whitespace
		try{
			platform.createAccount("smoked ham");
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (true);
		}

		// Testing erroneous case for create account for if handle is empty
		try{
			platform.createAccount("");
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (true);
		}

		// Testing erroneous case for create account for if handle is longer than 30 characters
		try{
			platform.createAccount("ham".repeat(11));
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (true);
		}

		//Test erroneous case for illegal handle exception
		try{
			platform.createAccount("hamham");
			platform.createAccount("hamham");
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (true);
		}
		catch (InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}




		// Testing typical case for create account
		try{
			platform.createAccount("createham", "tasty");
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		// Testing erroneous case for create account for if handle contains whitespace
		try{
			platform.createAccount("smoked ham", "tasty");
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (true);
		}

		// Testing erroneous case for create account for if handle is empty
		try{
			platform.createAccount("", "tasty");
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (true);
		}

		// Testing erroneous case for create account for if handle is longer than 30 characters
		try{
			platform.createAccount("ham".repeat(11), "tasty");
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e){
			assert (true);
		}

		//Test erroneous case for illegal handle exception
		try{
			platform.createAccount("illegalham", "tasty");
			platform.createAccount("illegalham", "tasty");
			assert (false) : "Account incorrectly created";
		}
		catch (IllegalHandleException e){
			assert (true);
		}
		catch (InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}



		// Test typical case for removing account
		try{
			platform.createAccount("removeham");
			platform.removeAccount("removeham");
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}

		// Testing erroneous case for removing account
		try{
			platform.removeAccount("grilledham");
			assert (false) : "Account removed incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (true);
		}




		// Test typical case for removing account
		try{
			int newId = platform.createAccount("removeham1"); 
			platform.removeAccount(newId);
		}
		catch(AccountIDNotRecognisedException e){
			assert (false) : "AccountIDNotRecognisedException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}

		// Testing erroneous case for removing account
		try{
			platform.removeAccount(-1);
			assert (false) : "Account removed incorrectly";
		}
		catch(AccountIDNotRecognisedException e){
			assert (true);
		}



		// Testing typical case for changing account handle
		try{
			platform.createAccount("changeham");
			platform.changeAccountHandle("changeham", "changedHam");
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}

		// Testing erroneous case for changing account handle when handle not recognised
		try{
			platform.changeAccountHandle("notrecognisedham", "changedHam");
			assert (false) : "Account handle changed incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (true);
		}

		// Testing erroneous case for changing account handle when new handle contains a whitespace
		try{
			platform.createAccount("changeham1");
			platform.changeAccountHandle("changeham1", "changed Ham");
			assert (false) : "Account handle changed incorrectly";
		}
		catch(InvalidHandleException e){
			assert (true);
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}


		// Testing erroneous case for changing account handle when new handle is empty
		try{
			platform.createAccount("changeham2");
			platform.changeAccountHandle("changeham2", "");
			assert (false) : "Account handle changed incorrectly";
		}
		catch(InvalidHandleException e){
			assert (true);
		}
		catch(IllegalHandleException e){
			assert (false) :  "IllegalHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}


		// Testing erroneous case for changing account handle when new handle is longer than 30 characters
		try{
			platform.createAccount("changeham3");
			platform.changeAccountHandle("changeham3", "ham".repeat(11));
			assert (false) : "Account handle changed incorrectly";
		}
		catch(InvalidHandleException e){
			assert (true);
		}
		catch(IllegalHandleException e){
			assert (false) :  "IllegalHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}

		// Testing erroneous case for changing account handle when new handle is invalid
		try{
			platform.createAccount("changeham4");
			platform.createAccount("invalidham");
			platform.changeAccountHandle("changeham4", "invalidham");
			assert (false) : "Account handle changed incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(IllegalHandleException e){
			assert (true);
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}

		

		// Testing typical case for updating account description
		try{
			platform.createAccount("changedescriptionham", "tasty");
			platform.updateAccountDescription("changedescriptionham", "meaty");
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		// Testing erroneous case for updating account description when handle not recognised
		try{
			platform.updateAccountDescription("notrecognisedham", "meaty");
			assert (false) : "Description changed incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (true);
		}




		// Testing typical case for showing an account
		try{
			int newId = platform.createAccount("showaccount");
			assert (platform.showAccount("showaccount").equals("ID: " + newId + "\n"+ "Handle: showaccount\nDescription: \nPost count: 0\nEndorse count: 0")) : "Show account string returned incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}

		// Testing erroneous case for showing account when handle not recognised
		try{
			platform.showAccount("notrecognisedham");
			assert (false) : "Account shown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (true);
		}


		// Testing typical case for creating a post
		try{
			platform.createAccount("postcreator");
			platform.createPost("postcreator", "look at this piece of ham");
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		// Testing erroneous case for when handle is not recognised
		try{
			platform.createPost("notrecognisedhandle", "look at this piece of ham");
			assert (false) : "Post created incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (true);
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		// Testing erroneous case when post message is empty
		try{
			platform.createAccount("postcreator1");
			platform.createPost("postcreator1", "");
			assert (false) : "Post created incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (true);
		}

		// Testing erroneous case when post message is longer than 100 characters
		try{
			platform.createAccount("postcreator2");
			platform.createPost("postcreator2", "ham".repeat(35));
			assert (false) : "Post created incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (true);
		}




		// Testing typical case when making an endorsement
		try{
			platform.createAccount("postcreator3");
			platform.createAccount("endorser");
			int postId = platform.createPost("postcreator3", "Endorse this if you agree ham is good");
			platform.endorsePost("endorser", postId);
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}
		catch(NotActionablePostException e){
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Testing erroneous case when making an endorsement and post id doesn't exist in the system
		try{
			platform.createAccount("endorser1");
			platform.endorsePost("endorser1", -1);
			assert (false) : "Endorsement incorrectly created";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (true);
		}
		catch(NotActionablePostException e){
			assert (false) : "NotActionablePostException thrown incorrectly";
		}
		
		// Testing erroneous case when making an endorsement on an endorsement
		try{
			platform.createAccount("postcreator4");
			platform.createAccount("endorser2");
			platform.createAccount("endorser3");
			int postId = platform.createPost("postcreator4", "Endorse this if you agree ham is good");
			int endorseId =	platform.endorsePost("endorser3", postId);
			platform.endorsePost("endorser2", endorseId);
			assert (false) : "Endorsement incorrectly created";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}
		catch(NotActionablePostException e){
			assert (true);
		}


		// Testing typical case for creating a comment
		try{
			platform.createAccount("postcreator5");
			platform.createAccount("commenter");
			int postId = platform.createPost("postcreator5", "look at this piece of ham");
			platform.commentPost("commenter", postId, "wow that is some ham");
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}
		catch(NotActionablePostException e){
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Testing erroneous case when making a comment and post id doesn't exist in the system
		try{
			platform.createAccount("commenter1");
			platform.commentPost("commenter", -1, "wow some tasty ham");
			assert (false) : "Comment incorrectly created";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (true);
		}
		catch(NotActionablePostException e){
			assert (false) : "NotActionablePostException thrown incorrectly";
		}
		catch (InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		} 

		// Testing erroneous case when making an comment on an endorsement
		try{
			platform.createAccount("postcreator6");
			platform.createAccount("endorser4");
			platform.createAccount("commenter2");
			int postId = platform.createPost("postcreator6", "Endorse this if you agree ham is good");
			int endorseId =	platform.endorsePost("endorser4", postId);
			platform.commentPost("commenter2", endorseId, "This comment is not allowed!");
			assert (false) : "Comment incorrectly created";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}
		catch(NotActionablePostException e){
			assert (true);
		}

		// Testing erroneous case when comment message is empty
		try{
			platform.createAccount("postcreator7");
			platform.createAccount("commenter3");
			int postId = platform.createPost("postcreator7", "Comment if you think ham is good");
			platform.commentPost("commenter3", postId, "");
			assert (false) : "Comment created incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (true);
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}
		catch(NotActionablePostException e){
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Testing erroneous case when comment message is longer than 100 characters
		try{
			platform.createAccount("postcreator8");
			platform.createAccount("commenter4");
			int postId = platform.createPost("postcreator8", "Comment if you think ham is good");
			platform.commentPost("commenter4", postId, "ham".repeat(34));
			assert (false) : "Comment created incorrectly";
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (true);
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}
		catch(NotActionablePostException e){
			assert (false) : "NotActionablePostException thrown incorrectly";
		}



		// Typical case for deleting a post
		try{
			platform.createAccount("postcreator9");
			int postId = platform.createPost("postcreator9", "Ham is terrible");
			platform.deletePost(postId);
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}

		// Erroneous case for deleting a post when post id doesn't exist in the system
		try{
			platform.deletePost(-1);
			assert (false) : "Post incorrectly deleted";
		}
		catch(PostIDNotRecognisedException e){
			assert (true);
		}

		// typical test for the getter of numberOfAccounts
		int numberAccounts = platform.getNumberOfAccounts();
		int id1 = -1;

		try{
			id1 = platform.createAccount("numberAccounts");
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		assert (numberAccounts + 1 == platform.getNumberOfAccounts()):"Account number is invalid";
		try{
			platform.removeAccount(id1);
		}
		catch(AccountIDNotRecognisedException e){
			assert (false) : "AccountIDNotRecognisedException thrown incorrectly";	
		}

		assert (numberAccounts == platform.getNumberOfAccounts()):"Account number is invalid";

		// typical test for the getter of TotalOriginalPosts
		int numberPosts = platform.getTotalOriginalPosts();
		int postId1 = -1;
		try{
			platform.createAccount("numberPosts");
			postId1 = platform.createPost("numberPosts", "post");
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		assert (numberPosts + 1 == platform.getTotalOriginalPosts()):"Post number is invalid";
		
		try{
			platform.deletePost(postId1);
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		assert (numberPosts == platform.getTotalOriginalPosts()):"Post number is invalid";
		
		// typical test for the getter of TotalEndorsementPost
		int numberEndorsements = platform.getTotalEndorsmentPosts();
		int postId2;
		int EndorsementId1 = -1;
		try{
			platform.createAccount("numberEndorsements");
			postId2 = platform.createPost("numberEndorsements", "post");
			EndorsementId1 = platform.endorsePost("numberEndorsements", postId2);
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		catch(NotActionablePostException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		assert (numberEndorsements + 1 == platform.getTotalEndorsmentPosts()):"Endorsement number is invalid";

		try{
			platform.deletePost(EndorsementId1);
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		assert (numberEndorsements == platform.getTotalEndorsmentPosts()):"Endorsement number is invalid";

		// typical test for the getter of TotalCommentPosts
		int numberComments = platform.getTotalCommentPosts();
		int postId3;
		int commentId1 = -1;
		try{
			platform.createAccount("numberComments");
			postId3 = platform.createPost("numberComments", "post");
			commentId1 = platform.commentPost("numberComments", postId3, "fine");
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		catch(NotActionablePostException e){
			assert (false) : "NotActionablePostException thrown incorrectly";
		}
		assert (numberComments + 1 == platform.getTotalCommentPosts()):"Endorsement number is invalid";

		try{
			platform.deletePost(commentId1);
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		assert (numberComments == platform.getTotalCommentPosts()):"Endorsement number is invalid";

		// typical test for the getter of MostEndorsedPost
		int postId4 = -1;
		try{
			platform.createAccount("MostEndorsedPost");
			postId4 = platform.createPost("MostEndorsedPost", "so fine");
			platform.createPost("MostEndorsedPost", "damn so fine");
			platform.endorsePost("MostEndorsedPost", postId4);
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		catch(NotActionablePostException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		assert (postId4 == platform.getMostEndorsedPost()) : "Incorrectly got mostEndorsedPost";

		// typical test for the getter of MostEndorsedAccount
		int id3 = -1;
		int postId6 = -1;
		try{
			id3 = platform.createAccount("MostEndorsedAccount1");
			platform.createAccount("MostEndorsedAccount2");
			postId6 = platform.createPost("MostEndorsedAccount1", "damn it's so fine");
			platform.createPost("MostEndorsedAccount2", "damn it is so fine");
			platform.endorsePost("MostEndorsedAccount1", postId6);
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		catch(NotActionablePostException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		assert (id3 == platform.getMostEndorsedAccount()) : "Incorrectly got mostEndorsedAccount";

		// typical test for ErasePlatform
		int postId8 = -1;
		try{
			platform.createAccount("erasePlatform");
			postId8 = platform.createPost("erasePlatform", "hmm");
			platform.endorsePost("erasePlatform", postId8);
			platform.commentPost("erasePlatform", postId8, "damn hmm");
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		catch(NotActionablePostException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		platform.erasePlatform();
		assert (platform.getNumberOfAccounts() == 0 && platform.getTotalCommentPosts() == 0 && platform.getTotalEndorsmentPosts() == 0 && platform.getTotalOriginalPosts() == 0) : "Incorrectly erased platform";

		// typical test for save and load platform
		int postId9 = -1;
		try{
			platform.createAccount("savePlatform");
			postId9 = platform.createPost("savePlatform", "damn oh so fine");
			platform.endorsePost("savePlatform", postId9);
			platform.commentPost("savePlatform", postId9, "oh");
		}
		catch(IllegalHandleException e){
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch(InvalidHandleException e){
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch(HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}
		catch(InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		}
		catch(PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		catch(NotActionablePostException e){
			assert (false) : "PostIDNotRecognisedException";
		}
		String filename = "test_save_platform.ser";
		try{
			platform.savePlatform(filename);
		}
		catch(IOException e){
			assert (false) : "IOException";
		}
		SocialMediaPlatform loadedPlatform = new SocialMedia();
		try{
			loadedPlatform.loadPlatform(filename);
		}
		catch(IOException e){
			assert (false) : "IOException";
		}
		catch(ClassNotFoundException e){
			assert (false) : "ClassNotFoundException";
		}
		assert (platform.getNumberOfAccounts() == loadedPlatform.getNumberOfAccounts() && platform.getTotalOriginalPosts() == loadedPlatform.getTotalOriginalPosts() && platform.getTotalCommentPosts() == loadedPlatform.getTotalCommentPosts() && platform.getTotalEndorsmentPosts() == loadedPlatform.getTotalEndorsmentPosts());
		File savedFile = new File(filename);
		savedFile.delete();
	}
}
