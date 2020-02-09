package com.furkanzayimoglu.mobilliumdemo.model;

import java.util.ArrayList;

public class ResponseModel {

    private ArrayList<DoctorModel> doctors = new ArrayList<>();

    public ArrayList<DoctorModel> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<DoctorModel> doctors) {
        this.doctors = doctors;
    }
}
