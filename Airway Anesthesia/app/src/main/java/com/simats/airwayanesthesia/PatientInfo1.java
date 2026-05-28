// PatientInfo1.java
package com.simats.airwayanesthesia;

public class  PatientInfo1 {
    private String id;
    private String name;
    private String gender;
    private String phno;
    private String profilePhoto;

    public PatientInfo1(String id, String name, String gender, String phno, String profilePhoto) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phno = phno;
        this.profilePhoto = profilePhoto;
    }

    // Add getters for each field
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhno() {
        return phno;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }
}
