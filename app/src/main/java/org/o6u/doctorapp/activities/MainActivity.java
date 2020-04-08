package org.o6u.doctorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.o6u.doctorapp.R;
import org.o6u.doctorapp.customViews.CustomTextView;
import org.o6u.doctorapp.fragments.CourseFragment;
import org.o6u.doctorapp.registrationActivities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    //Toolbar
    private Toolbar mToolbar;
    private CustomTextView titleToolbarTextView;

    //Database
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new CourseFragment();
                    titleToolbarTextView.setText("Courses");
                    break;
                case R.id.navigation_dashboard:
                    break;
                case R.id.navigation_notifications:
                    break;
            }
            if (fragment != null) {
                FragmentTransaction fc = getSupportFragmentManager().beginTransaction();
                fc.replace(R.id.main_frame_layout, fragment);
                fc.commit();
                return true;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        titleToolbarTextView = findViewById(R.id.toolbar_title_text_view);
        titleToolbarTextView.setText("O6U - Doctor App");

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(mAuth.getCurrentUser().getUid());

            //Start First Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_frame_layout, new CourseFragment())
                    .commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sign_out_btn) {
            FirebaseAuth.getInstance().signOut();
            sendToStart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
