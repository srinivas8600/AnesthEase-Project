// P1.java
package com.simats.airwayanesthesia;

public class P1 {
    private String id;
    private String name;
    private String gender;
    private String phno;
    private String profilePhoto;

    public P1(String id, String name, String gender, String phno, String profilePhoto) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phno = phno;
        this.profilePhoto = profilePhoto;
    }

    public PatientInfo1 toPatientInfo() {
        return new PatientInfo1(id, name, gender, phno, profilePhoto);
    }
}
