package socialmedia;

import java.util.ArrayList;

/**
 * The post class is used to create objects that represent users original posts on social media
 * 
 * @author Jonathan Rutland & Daniel Stirling Barros
 * @version 1.0
 */
public class Post {
    private int id;
    private String message;
    private Account poster;
    private ArrayList<Endorsement> endorsements = new ArrayList<Endorsement>();
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private static int CURRENT_ID=0;
    public static int NUMBER_POSTS=0;

    public Post(Account poster, String message){
        this.poster = poster;
        this.message = message;
        this.id = CURRENT_ID;
        CURRENT_ID++;
        NUMBER_POSTS++;
        poster.addPost(this);
    }

    public int getId(){
        return this.id;
    }

    public String getMessage(){
        return this.message;
    }

    public void delete(){
        for(Endorsement endorsement : endorsements){
            endorsement.delete();
        }
        for(Comment comment : comments){
            comment.setOriginalPost(null);
        }
        poster.removePost(this);

        NUMBER_POSTS --;
    }

    public String toString(){
        return "";
    }

    public StringBuilder showChildren(){
        return new StringBuilder();

    }

    public int getNumberOfEndorsements(){
        return endorsements.size();
    }

    public Account getPoster(){
        return poster;
    }

    public void resetCounters(){
        
    }

    public void addEndorsement(Endorsement endorsement){
        endorsements.add(endorsement);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeEndorsement(Endorsement endorsement){
        int index = -1;
        for(int i = 0; i < endorsements.size(); i++){
            if(endorsements.get(i) == endorsement){
                index = i;
            }
        }
        endorsements.remove(index);
    }

    public void removeComment(Comment comment){
        int index = -1;
        for(int i = 0; i < comments.size(); i++){
            if(comments.get(i) == comment){
                index = i;
            }
        }
        comments.remove(index);
    }
}

