package mattsaiki.chat;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matthew on 7/24/2017.
 */

public class GroupBucket {
    private static GroupBucket sGroupBucket;
    private List<Group> mGroups;

    public void addGroup(Group g){
        mGroups.add(g);
    }

    public static GroupBucket get(Context context) {
        if(sGroupBucket == null) {
            sGroupBucket = new GroupBucket(context);
        }
        return sGroupBucket;
    }

    private GroupBucket(Context context){
        mGroups = new ArrayList<>();
    }

    public List<Group> getGroups() {
        return mGroups;
    }

    public Group getGroup(UUID id) {
        for(Group group : mGroups){
            if(group.getId().equals(id)) {
                return group;
            }
        }
        return null;
    }
}