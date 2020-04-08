package org.o6u.doctorapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.o6u.doctorapp.R;
import org.o6u.doctorapp.adapters.CourseAdapter;
import org.o6u.doctorapp.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    private RecyclerView mCoursesList;
    private DatabaseReference mDatabaseRef;
    private List<Course> courses;
    private CourseAdapter adapter;

    private FirebaseAuth mAuth;

    private String userID;

    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_course, container, false);

        //Define RecyclerView Elements
        mCoursesList = mView.findViewById(R.id.courses_recyclerView);
        mCoursesList.setHasFixedSize(true);
        mCoursesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        courses = new ArrayList<>();
        adapter = new CourseAdapter(getActivity(), courses);
        mCoursesList.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        getStudentsData();

        return mView;
    }

    //Get Data In RecyclerView
    private void getStudentsData(){

        //KeepSynced
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Teaches").child(userID);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Course course = snapshot.getValue(Course.class);
                    courses.add(course);
                }
                adapter.notifyDataSetChanged();
                mDatabaseRef.keepSynced(true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error In Connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
