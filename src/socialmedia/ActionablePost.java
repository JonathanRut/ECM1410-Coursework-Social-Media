package socialmedia;
import java.util.ArrayList;

/**
 * The ActionablePost class provides an interface which allows actionable posts to be created easily
 * 
 * @author Jonathan Rutland and Daniel Stirling Barros
 * @version 1.0
 */
public abstract class ActionablePost extends Post {
    /**
     * A protected list {@link ArrayList} containing elements of type {@link Endorsement} used to store the endorsements of a post
     */
    protected ArrayList<Endorsement> endorsements = new ArrayList<Endorsement>();

     /**
     * A protected list {@link ArrayList} containing elements of type {@link Comment} used to store the comment of a post
     */
    protected ArrayList<Comment> comments = new ArrayList<Comment>();

    /**
     * This method returns a string of information about the Individual post with a given indent
     * @param indent the number of indents needed
     * @return the string of information about the post 
     */
    private String toString(int indent){
        // A pre condition checks if the given indent is valid
        if(indent < 0){
            throw new IllegalArgumentException("Indent cannot be less than 0");
        }

        // A new StringBuilder is created and then information about the Individual post is appended to it with an indent
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ".repeat((indent - 1) * 4) + "| > ").append("ID: ").append(ID).append("\n");
		stringBuilder.append(" ".repeat(indent * 4)).append("Account: ").append(poster.getHandle()).append("\n");
		stringBuilder.append(" ".repeat(indent * 4)).append("No. endorsements: ").append(getNumberOfEndorsements()).append(" | ");
		stringBuilder.append("No. comments: ").append(comments.size()).append("\n");
		stringBuilder.append(" ".repeat(indent * 4)).append(message);

        // The information is returned as a string
        return stringBuilder.toString();
    }

    /**
     * This method recursively generates a string builder with details of the current posts and its children posts
     * @param indent the indent to be applied to the details of the children
     * @return A string builder of the children posts
     */
    public StringBuilder showChildren(int indent){
        // A pre condition checks if the given indent is valid
        if(indent < 0){
            throw new IllegalArgumentException("Indent cannot be less than 0");
        }

        // A new string builder is instantiated
        StringBuilder stringBuilder = new StringBuilder();

        // If this is the current post then no indent is needed
        if(indent == 0){
            stringBuilder.append(toString());
        }
        // Otherwise an indent is applied to the details
        else{
            stringBuilder.append(toString(indent));
        }
        stringBuilder.append("\n");

        // This for loop iterates through the comments of the post
        for(int i = 0; i < comments.size(); i++){
            // An index and and a bar is added then the details about the comments are added to the string builder
            stringBuilder.append("    ".repeat(indent)+"|\n");
            stringBuilder.append(comments.get(i).showChildren(indent + 1).toString());
        }
        // Finally the string builder is returned
        return stringBuilder;
    }

    @Override
    public String toString(){
        // A new StringBuilder is created and then information about the Individual post is appended to it
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ID: ").append(ID).append("\n");
		stringBuilder.append("Account: ").append(poster.getHandle()).append("\n");
		stringBuilder.append("No. endorsements: ").append(getNumberOfEndorsements()).append(" | ");
		stringBuilder.append("No. comments: ").append(comments.size()).append("\n");
		stringBuilder.append(message);

        // The information is returned as a string
        return stringBuilder.toString();
    }

    /**
     * This method gets the number of endorsements on the post
     * @return the number of endorsements returned
     */
    public int getNumberOfEndorsements(){
        return endorsements.size();
    }

    /**
     * This method adds an endorsement to the endorsements list
     * @param endorsement the endorsement to be added
     */
    public void addEndorsement(Endorsement endorsement){
        endorsements.add(endorsement);
        assert (endorsements.contains(endorsement)) : "Endorsement not added successfully";
    }
    /**
     * This method adds a comment to the comments list
     * @param comment the comment to be added 
     */
    public void addComment(Comment comment){
        comments.add(comment);
        assert (comments.contains(comment)) : "Comment not added successfully";
    }
    /**
     * This method removes an endorsement from the endorsements list 
     * @param endorsement the endorsement to be removed
     */
    public void removeEndorsement(Endorsement endorsement){
        // this for loop iterates through each element in the endorsements list
        int index = -1;
        for(int i = 0; i < endorsements.size(); i++){
            // this if statement gets the index of the endorsement by comparing the ith element in the endorsement list
            if(endorsements.get(i) == endorsement){
                index = i;
            }
        }
        // Assertion makes sure the endorsement is removed successfully
        assert (index >= 0) : "Endorsement not removed successfully";

        // this removes the endorsement from the endorsement list
        endorsements.remove(index);


    }
    /**
     * This method removes a comment from the comments list
     * @param comment the comment to be removed
     */
    public void removeComment(EmptyPost comment){
        // this for loop iterates through each element in the comments list
        int index = -1;
        for(int i = 0; i < comments.size(); i++){
            // this if statement gets the index of the comment by comparing the ith element in the comments list
            if(comments.get(i) == comment){
                index = i;
            }
        }
        // Assertion makes sure that the comment is removed successfully
        assert (index >= 0) : "Comment not removed successfully";

        // this removes the comments from the comments list
        comments.remove(index);
    }


    @Override
    protected void delete(){
        // A while loop iterates through the endorsements and deletes them
        while(!endorsements.isEmpty()){
            Endorsement endorsement = endorsements.get(0);
            endorsement.delete();
        }
        // For each comment in the comment list the commented post is set to an empty post
        for(Comment comment : comments){
            comment.setCommentedPost(new EmptyPost());
        }
        // The comment list is cleared
        comments.clear();

        // Assertion makes user that the deletion was successful
        assert (endorsements.size() == 0 && comments.size() == 0) : "Post not deleted successfully";
    }

    /**
     * Get the list of endorsements for the post
     * @return the list of endorsements
     */
    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

}
