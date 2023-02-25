package socialmedia;

public class Endorsement extends Post{
    private Post originalPost;

    public Endorsement(Account poster, Post originalPost){
        super(poster, originalPost.getMessage());
        this.originalPost = originalPost;
        originalPost.addEndorsement(this);
    }

    @Override
    public void delete(){
        originalPost.removeEndorsement(this);
    }

}
