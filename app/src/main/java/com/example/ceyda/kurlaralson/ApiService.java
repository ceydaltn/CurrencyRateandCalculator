package com.example.ceyda.kurlaralson;

import com.example.ceyda.kurlaralson.Doviz;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {
    @GET("embed/doviz.json")
    Call<Doviz> getDoviz();
}
