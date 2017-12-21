package mattsaiki.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Matthew on 7/25/2017.
 * This will display a list of Groups using RecyclerView
 */
public class GroupListFragment extends Fragment {
    private RecyclerView mGroupRV;
    private GroupAdapter mAdapter;

    //Tell the FragmentManager that it will receive menu callbacks
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //Connecting view to the fragment
    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle savedInstanceState){
        View v = li.inflate(R.layout.fragment_group_list, vg, false);
        mGroupRV = (RecyclerView) v.findViewById(R.id.group_recycler_view);
        mGroupRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    //This method is called by hosting activity when the fragment needs to be reloaded in case there were changes made
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Groups");
    }

    //This method inflates the menu for adding a new group
    @Override
    public void onCreateOptionsMenu(Menu m, MenuInflater mi){
        super.onCreateOptionsMenu(m, mi);
        mi.inflate(R.menu.fragment_group_list, m);
    }

    //This method responds to when user clicks New Group
    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case R.id.new_group:
                Group g = new Group();
                GroupBucket.get(getActivity()).addGroup(g);
                Intent intent = GroupSetupActivity.newIntent(getActivity(), g.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(mi);
        }
    }

    //Sets up the fragments UI
    private void updateUI() {
        GroupBucket groupBucket = GroupBucket.get(getActivity());
        List<Group> groups = groupBucket.getGroups();
        String name;
        //This deletes the groups that were automatically added without being created
        for(int i = 0; i < groups.size(); i++){
            if(groups.get(i).getWasCreated() == 0){
                groups.remove(i);
            }
        }
        mAdapter = new GroupAdapter(groups);
        mGroupRV.setAdapter(mAdapter);
    }

    //Define the ViewHolder to inflate the layout
    private class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public static final String GROUP_ID  ="GROUP_ID";
        private TextView mGroupName;
        private Group mGroup;
        private TextView mNumPosts;
        private TextView mLastPostDate;
        private ImageButton mGroupSettings;

        public GroupHolder(LayoutInflater li, ViewGroup vg){
            super(li.inflate(R.layout.list_item_group, vg, false));
            itemView.setOnClickListener(this);
            //Pull out widgets that need to be bound
            mGroupName = (TextView) itemView.findViewById(R.id.group_title);
            mNumPosts = (TextView) itemView.findViewById(R.id.group_num_posts);
            mLastPostDate = (TextView) itemView.findViewById(R.id.last_post_date);
            mGroupSettings = (ImageButton) itemView.findViewById(R.id.group_settings);
        }

        //Bind method, called every time a new Group is displayed in the GroupHolder
        public void bind(Group g){
            mGroup = g;
            mGroupName.setText(mGroup.getGroupName());
            if(mGroup.getNumPosts() == 1){
                mNumPosts.setText(String.valueOf(mGroup.getNumPosts()) + " post");
            }
            else{
                mNumPosts.setText(String.valueOf(mGroup.getNumPosts()) + " posts");
            }
            if(mGroup.getNumPosts() > 0){
                mLastPostDate.setText("Last post:" + mGroup.getLastPostDate().toString());
            }

            mGroupSettings.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(GROUP_ID, mGroup.getId());
                    GroupSettingsFragment nextFrag= new GroupSettingsFragment();
                    nextFrag.setArguments(bundle);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, nextFrag, "tag");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }

        //When a group is clicked, it will start GroupPostFragment
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(GROUP_ID, mGroup.getId());
            GroupPostFragment nextFrag= new GroupPostFragment();
            nextFrag.setArguments(bundle);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, nextFrag, "tag");
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    //Create the adapter
    private class GroupAdapter extends RecyclerView.Adapter<GroupHolder> {
        private List<Group> mGroups;
        public GroupAdapter(List<Group> groups){
            mGroups=groups;
        }

        @Override
        public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new GroupHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(GroupHolder holder, int position) {
            Group group = mGroups.get(position);
            holder.bind(group);
        }

        @Override
        public int getItemCount() {
            return mGroups.size();
        }
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}