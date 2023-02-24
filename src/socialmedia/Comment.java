package socialmedia;

public class Comment extends Post{
    private Post originalPost;

    public Comment(Account poster, Post originalpost, String message){
        super(poster, message);
        this.originalPost = originalpost;
    }


}
