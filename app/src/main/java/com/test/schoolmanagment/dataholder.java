package com.test.schoolmanagment;

public class dataholder {
    String classlink,videoslink,meetinglink,quizlink;

    public String getQuizlink() {
        return quizlink;
    }

    public void setQuizlink(String quizlink) {
        this.quizlink = quizlink;
    }

    public dataholder() {

    }

    public String classnam,emailname,passworname;

    public dataholder(String classnam, String emailname, String passworname) {
        this.classnam = classnam;
        this.emailname = emailname;
        this.passworname = passworname;
    }

    public String getClassnam() {
        return classnam;
    }

    public void setClassnam(String classnam) {
        this.classnam = classnam;
    }

    public String getEmailname() {
        return emailname;
    }

    public void setEmailname(String emailname) {
        this.emailname = emailname;
    }

    public String getPassworname() {
        return passworname;
    }

    public void setPassworname(String passworname) {
        this.passworname = passworname;
    }

    public dataholder(String meetinglink) {
        this.meetinglink = meetinglink;
    }

    public dataholder(String classlink, String videoslink) {
        this.classlink = classlink;
        this.videoslink = videoslink;
    }


    public String getClasslink() {
        return classlink;
    }

    public void setClasslink(String classlink) {
        this.classlink = classlink;
    }

    public String getVideoslink() {
        return videoslink;
    }

    public void setVideoslink(String videoslink) {
        this.videoslink = videoslink;
    }

    public String getMeetinglink() {
        return meetinglink;
    }

    public void setMeetinglink(String meetinglink) {
        this.meetinglink = meetinglink;
    }




}
