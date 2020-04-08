package org.o6u.doctorapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.o6u.doctorapp.R;
import org.o6u.doctorapp.customViews.CustomTextView;
import org.o6u.doctorapp.models.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>
{
    List<Course> courses;
    Context context;

    public CourseAdapter(Context context, List<Course> courses)
    {
        this.courses = courses;
        this.context = context;
    }

    private Context getContext()
    {
        return context;
    }

    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position)
    {
        Course course = courses.get(position);
        holder.courseNameTextView.setText(course.getCourseName());
        holder.lectureTimeTextView.setText("Date & Time: " +course.getLectureTime());
        holder.techYearTextView.setText("Academic Year: " + course.getTeachYear());
        holder.roomTextView.setText("Lecture Room: " + course.getRoom());
    }

    @Override
    public int getItemCount()
    {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CustomTextView courseNameTextView;
        CustomTextView lectureTimeTextView;
        CustomTextView techYearTextView;
        CustomTextView roomTextView;

        ViewHolder (View view)
        {
            super(view);

            courseNameTextView = view.findViewById(R.id.item_course_name_textView);
            lectureTimeTextView = view.findViewById(R.id.item_course_lecture_time_textView);
            techYearTextView = view.findViewById(R.id.item_course_teach_year_textView);
            roomTextView = view.findViewById(R.id.item_course_room_textView);
        }

        @Override
        public void onClick(View v) {
        }
    }
}

