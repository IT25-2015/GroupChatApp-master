package mattsaiki.chat;

import android.support.v4.app.Fragment;

public class GroupListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new GroupListFragment();
    }
}

