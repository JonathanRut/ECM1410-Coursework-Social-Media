package socialmedia;

public class Endorsement extends Post{
    private Post originalPost;

    public Endorsement(Account poster, Post originalPost){
        super(poster, originalPost.getMessage());
        this.originalPost = originalPost;
    }

}
