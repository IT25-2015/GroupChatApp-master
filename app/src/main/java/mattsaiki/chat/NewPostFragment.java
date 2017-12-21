package mattsaiki.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.UUID;

/**
 * Created by Matthew on 7/31/2017.
 */

public class NewPostFragment extends Fragment {
    public static final String G_ID="G_ID";
    public static final String P_ID="P_ID";

    private Post mPost;
    private EditText mPostTitle;
    private Button mPostToGroup;
    private EditText mPostDescription;
    private UUID mGroupId;
    private UUID mPostId;
    private Group mGroup;

    @Override
    public void onResume(){
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("New Post");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    //This is called by NewPostActivity, the hosting activity
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            mGroupId = (UUID) bundle.getSerializable(G_ID);
            mGroup = GroupBucket.get(getActivity()).getGroup(mGroupId);
            mPostId = (UUID) bundle.getSerializable(P_ID);
            mPost = mGroup.getPosts().getPost(mPostId);
        }
    }

    //This method inflates the layout for the fragment's view and returns the inflated View to the hosting activity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        /*
        *Explicityly inflate fragment's view
        *Params: layout resource ID, view's parent,
        *tell layout inflater whether to add the inflated view to the view's parent
         */
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        //Wiring up the EditText widget for the post title
        mPostTitle = (EditText) view.findViewById(R.id.post_title);
        mPostTitle.setText(mPost.getPostTopic());
        mPostTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPost.setPostTopic(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPostDescription = (EditText) view.findViewById(R.id.post_description);
        mPostDescription.setText(mPost.getPostDescription());
        mPostDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPost.setPostDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //When Post To Group is pressed, set wasPosted to 1 (indicate group was created) and go back to hosting activity
        mPostToGroup = (Button) view.findViewById(R.id.post_content);
        mPostToGroup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mGroup.getPosts().getPost(mPostId).setWasPosted(1);
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
