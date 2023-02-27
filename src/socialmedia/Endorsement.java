package socialmedia;

public class Endorsement extends Post{
    private Post originalPost;

    public Endorsement(Account poster, Post originalPost){
        super(poster, originalPost.getMessage());
        this.originalPost = originalPost;
        originalPost.addEndorsement(this);
        Account endorsedAccount = originalPost;
        originalPost.getPoster();
    }

    @Override
    public void delete(){
        originalPost.removeEndorsement(this);
        NUMBER_POSTS--;
    }

}
