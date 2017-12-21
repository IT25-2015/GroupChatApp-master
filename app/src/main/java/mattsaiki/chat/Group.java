package mattsaiki.chat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Matthew on 7/24/2017.
 */

public class Group {
    private String mGroupName;
    private UUID mId;
    private Date mLastPostDate;
    private int mNumPosts=0;
    private int wasCreated=0;
    private PostBucket mGroupPosts;

    public Group(){
        mId = UUID.randomUUID();
        mGroupPosts = new PostBucket();
    }

    public void addPost(Post post){
        mGroupPosts.addPost(post);
        mNumPosts++;
        setLastPostDate(new Date());
    }

    public int getNumPosts() {
        return mNumPosts;
    }

    public void setNumPosts(int numPosts) {
        mNumPosts = numPosts;
    }

    public PostBucket getPosts(){
        return mGroupPosts;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public UUID getId() {
        return mId;
    }

    public int getWasCreated() {
        return wasCreated;
    }

    public void setWasCreated(int wasCreated) {
        this.wasCreated = wasCreated;
    }

    public Date getLastPostDate() {
        return mLastPostDate;
    }

    public void setLastPostDate(Date lastPost) {
        mLastPostDate = lastPost;
    }
}
