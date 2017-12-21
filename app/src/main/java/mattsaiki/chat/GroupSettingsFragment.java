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
 * Created by Matthew on 8/3/2017.
 */
public class GroupSettingsFragment extends Fragment {
    public static final String GROUP_ID  ="GROUP_ID";
    private UUID mGroupId;
    private Group mGroup;
    private EditText mEditGroupName;
    private Button mMakeChanges;
    private String mNameChange;
    boolean mNameWasChanged=false;
    boolean mUpdate = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle savedInstanceState) {
        View v = li.inflate(R.layout.group_settings, vg, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            mGroupId = (UUID) bundle.getSerializable(GROUP_ID);
            mGroup = GroupBucket.get(getActivity()).getGroup(mGroupId);
        }

        mEditGroupName = (EditText) v.findViewById(R.id.edit_group_name);
        mEditGroupName.setText(mGroup.getGroupName());
        mEditGroupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
                    mNameChange = s.toString();
                    mNameWasChanged = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMakeChanges = (Button) v.findViewById(R.id.update_settings);
        mMakeChanges.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mNameWasChanged == true){
                    //GroupBucket.get(getActivity()).getGroup(mGroupId).setGroupName(mNameChange);
                    mGroup.setGroupName(mNameChange);
                    mUpdate = true;
                }
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Settings");
    }
}
