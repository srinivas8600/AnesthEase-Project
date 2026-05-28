// d1.java
package com.simats.airwayanesthesia;

public class d1 {
    private String id;
    private String name;
    private String gender;
    private String phno;
    private String profile;

    public d1(String id, String name, String gender, String phno,String profile) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phno = phno;
        this.profile = profile;
    }

    public DoctorInfo1 toDoctorInfo() {
        return new DoctorInfo1(id, name, gender, phno,profile);
    }
}
