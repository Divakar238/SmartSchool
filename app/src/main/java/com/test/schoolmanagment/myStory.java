package com.test.schoolmanagment;

public class myStory {
    String mcneeseId,studentName;

    public myStory(String mcneeseId, String studentName) {
        this.mcneeseId = mcneeseId;
        this.studentName = studentName;
    }
    public myStory() {

    }

    public String getMcneeseId() {
        return mcneeseId;
    }

    public void setMcneeseId(String mcneeseId) {
        this.mcneeseId = mcneeseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
