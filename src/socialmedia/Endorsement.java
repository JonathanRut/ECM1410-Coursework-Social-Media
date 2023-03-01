package socialmedia;

public class Endorsement extends Post{
    private Post endorsedPost;

    public Endorsement(Account poster, Post endorsedPost){
        super(poster, endorsedPost.getMessage());
        this.endorsedPost = endorsedPost;
        endorsedPost.addEndorsement(this);
        Account endorsedAccount = endorsedPost.getPoster();
        endorsedAccount.incrementEndorsements();
    }

    @Override
    public void delete(){
        endorsedPost.removeEndorsement(this);
        numberPosts--;
        Account endorsedAccount = endorsedPost.getPoster();
        endorsedAccount.decrementEndorsements();
    }

}
