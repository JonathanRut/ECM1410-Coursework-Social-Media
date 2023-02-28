package socialmedia;

public class Comment extends Post{
    private Post originalPost;

    public Comment(Account poster, Post originalpost, String message){
        super(poster, message);
        this.originalPost = originalpost;
        originalpost.addComment(this);
    }

    public void setOriginalPost(Post originalPost){
        this.originalPost = originalPost;
    }

    @Override
    public void delete(){
        super.delete();
        originalPost.removeComment(this);
        originalPost = null;
    }
}
