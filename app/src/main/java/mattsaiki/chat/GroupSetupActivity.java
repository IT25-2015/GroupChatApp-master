package mattsaiki.chat;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Matthew on 7/24/2017.
 */

public class GroupSetupActivity extends SingleFragmentActivity{
    public static final String extra = "mattsaiki.chat.group_id";
    public static Intent newIntent(Context context, UUID id) {
        Intent i = new Intent(context, GroupSetupActivity.class);
        i.putExtra(extra, id);
        return i;
    }

    //Create a new GroupSetupFragment, pass in the ID it received as an extra
    @Override
    protected Fragment createFragment(){
        UUID groupId = (UUID) getIntent().getSerializableExtra(extra);
        return GroupSetupFragment.newInstance(groupId);
    }
}
