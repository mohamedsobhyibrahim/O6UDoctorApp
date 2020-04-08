package org.o6u.doctorapp.models;

import java.io.Serializable;

public class Course implements Serializable {

    private String courseName;
    private String teachYear;
    private String lectureTime;
    private String room;

    public Course(String courseName, String teachYear, String lectureTime, String room) {
        this.courseName = courseName;
        this.teachYear = teachYear;
        this.lectureTime = lectureTime;
        this.room = room;
    }

    public Course() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeachYear() {
        return teachYear;
    }

    public void setTeachYear(String teachYear) {
        this.teachYear = teachYear;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
