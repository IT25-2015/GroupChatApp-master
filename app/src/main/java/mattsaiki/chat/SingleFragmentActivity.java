package mattsaiki.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Matthew on 7/25/2017.
 * This will be used by activities, it is an abstract activity
 * Sets activity's view to be inflated from activity_fragment, look for the fragment and create it if not found
 * Subclasses will use this method to return an instance of the fragment the activity is hosting
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_fragment);

        /*
         * FragmentManager will manage the fragments and add the view's to the activity's view hierarchy
         * Create a fragment transaction
         */
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}