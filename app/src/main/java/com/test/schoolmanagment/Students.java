package com.test.schoolmanagment;

public class Students {
    private String studenId;
    private String studentName;
    private String mcneeseId;

    public Students(){

    }

    public Students(String studenId, String studentName, String mcneeseId) {
        this.studenId = studenId;
        this.studentName = studentName;
        this.mcneeseId = mcneeseId;
    }

    public void setStudenId(String studenId) {
        this.studenId = studenId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setMcneeseId(String mcneeseId) {
        this.mcneeseId = mcneeseId;
    }

    public String getStudenId() {
        return studenId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getMcneeseId() {
        return mcneeseId;
    }
}
