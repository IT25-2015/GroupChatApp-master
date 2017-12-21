package mattsaiki.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import java.util.UUID;


/**
 * Created by Matthew on 7/31/2017.
 * This displays a list of posts of a certain group
 */

public class GroupPostFragment extends Fragment {
    public static final String GROUP_ID  ="GROUP_ID";
    public static final String G_ID="G_ID";
    public static final String P_ID="P_ID";

    private RecyclerView mRV;
    private PostAdapter mPostAdapter;
    private UUID mGroupId;
    private Group mGroup;

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Posts");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            mGroupId = (UUID) bundle.getSerializable(GROUP_ID);
            mGroup = GroupBucket.get(getActivity()).getGroup(mGroupId);
            updateUI();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle savedInstanceState) {
        View v = li.inflate(R.layout.fragment_post_list, vg, false);
        mRV = (RecyclerView) v.findViewById(R.id.post_recycler_view);
        mRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    public void updateUI(){
        //PostBucket postBucket = PostBucket.get(getActivity());
        if(mGroup.getPosts() != null) {
            PostBucket postBucket = mGroup.getPosts();
            List<Post> posts = postBucket.getPosts();
            //This deletes the groups that were automatically added without being created
            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getWasPosted() == 0) {
                    posts.remove(i);
                }
            }
            mPostAdapter = new PostAdapter(posts);
            mRV.setAdapter(mPostAdapter);
        }
    }

    private class PostHolder extends RecyclerView.ViewHolder {
        private TextView mPostTitle;
        private TextView mDate;
        private Post mPost;
        private TextView mNumComments;

        public void bind(Post post){
            mPost = post;
            mPostTitle.setText(mPost.getPostTopic());
            mDate.setText(mPost.getDatePosted().toString());
            mNumComments.setText(String.valueOf(mPost.getNumComments()));
        }

        public PostHolder(LayoutInflater li, ViewGroup vg){
            super(li.inflate(R.layout.list_item_post, vg, false));
            mPostTitle = (TextView) itemView.findViewById(R.id.post_title);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mNumComments = (TextView) itemView.findViewById(R.id.post_comments_num);
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder>{
        private List<Post> mPostList;
        public PostAdapter(List<Post> posts){
            mPostList = posts;
        }

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new PostHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
            Post post = mPostList.get(position);
            holder.bind(post);
        }

        @Override
        public int getItemCount() {
            return mPostList.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_post_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case R.id.new_post:
                Post post = new Post();
                mGroup.addPost(post);

                Bundle bundle = new Bundle();
                bundle.putSerializable(G_ID, mGroup.getId());
                bundle.putSerializable(P_ID, post.getId());

                NewPostFragment nextFrag= new NewPostFragment();
                nextFrag.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, nextFrag, "tag");
                ft.addToBackStack(null);
                ft.commit();

                return true;
            default:
                return super.onOptionsItemSelected(mi);
        }
    }
}

