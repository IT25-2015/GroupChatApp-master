package mattsaiki.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Matthew on 7/24/2017.
 * This is the java class for the new conversation fragment
 * This is a controller that interacts with model and view objects, it presents the UI to create a new group
 */

public class GroupSetupFragment extends Fragment {
    private static final String arg_id = "group_id";
    private Group mGroup;
    private EditText mGroupName;
    private Button mCreate;
    private UUID mId;

    //This method accepts an ID, creates an arg bundle and fragment instance and attaches the two
    public static GroupSetupFragment newInstance(UUID groupId){
        Bundle arg = new Bundle();
        arg.putSerializable(arg_id, groupId);
        GroupSetupFragment f = new GroupSetupFragment();
        f.setArguments(arg);
        return f;
    }

    //This is called by GroupListActivity, the hosting activity
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //retrieve an extra from the activity
        UUID groupId = (UUID) getArguments().getSerializable(arg_id);
        mId = groupId;
        mGroup = GroupBucket.get(getActivity()).getGroup(groupId);
    }

    //This method inflates the layout for the fragment's view and returns the inflated View to the hosting activity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        /*
        *Explicityly inflate fragment's view
        *Params: layout resource ID, view's parent,
        *tell layout inflater whether to add the inflated view to the view's parent
         */
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        //Wiring up the EditText widget for the Group name
        mGroupName = (EditText) view.findViewById(R.id.group_name);
        mGroupName.setText(mGroup.getGroupName());
        mGroupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGroup.setGroupName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Wiring up the ImageView and ImageButtons for the Group photo
        //mGroupPhotoButton = (ImageButton) view.findViewById(R.id.group_camera);
        //mGroupPhotoView = (ImageView) view.findViewById(R.id.group_photo);

        //When Create is pressed, set wasCreated to 1 (indicate group was created) and go back to hosting activity
        mCreate = (Button) view.findViewById(R.id.create_group);
        mCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GroupBucket.get(getActivity()).getGroup(mId).setWasCreated(1);
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}