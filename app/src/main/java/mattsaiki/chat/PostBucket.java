package mattsaiki.chat;

import android.content.Context;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Matthew on 7/31/2017.
 */

public class PostBucket {
    private static PostBucket sPostBucket;
    private ArrayList<Post> mPosts;

    public PostBucket(){
        mPosts = new ArrayList<Post>();
    }

    public static PostBucket get(Context context) {
        if(sPostBucket == null){
            sPostBucket = new PostBucket(context);
        }
        return sPostBucket;
    }

    public int size(){
        return mPosts.size();
    }

    public void addPost(Post p){
        mPosts.add(p);
    }

    private PostBucket(Context context){
        mPosts = new ArrayList<>();
    }

    public ArrayList<Post> getPosts(){
        return mPosts;
    }

    public Post getPost(UUID id){
        for(Post post: mPosts){
            if(post.getId().equals(id)){
                return post;
            }
        }
        return null;
    }

}
