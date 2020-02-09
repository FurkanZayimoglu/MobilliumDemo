package com.furkanzayimoglu.mobilliumdemo.network;

import com.furkanzayimoglu.mobilliumdemo.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DoctorApiService {


    @GET("doctors.json")
    Call<ResponseModel> getDoctors();
}
