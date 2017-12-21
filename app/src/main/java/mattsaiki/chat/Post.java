package mattsaiki.chat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Matthew on 7/31/2017.
 */

public class Post {
    private int mNumComments;
    private String mPostTopic;
    private UUID mId;
    private Date mDatePosted;
    private int wasPosted;
    private String mPostDescription;

    public Post(){
        mId = UUID.randomUUID();
        mDatePosted = new Date();
        mNumComments = 0;
    }

    public int getNumComments() {
        return mNumComments;
    }

    public void setNumComments(int numComments) {
        mNumComments = numComments;
    }

    public String getPostTopic() {
        return mPostTopic;
    }

    public void setPostTopic(String postTopic) {
        mPostTopic = postTopic;
    }

    public UUID getId() {
        return mId;
    }

    public Date getDatePosted() {
        return mDatePosted;
    }

    public String getPostDescription() {
        return mPostDescription;
    }

    public void setPostDescription(String postDescription) {
        mPostDescription = postDescription;
    }

    public int getWasPosted() {
        return wasPosted;
    }

    public void setWasPosted(int wasPosted) {
        this.wasPosted = wasPosted;
    }
}
