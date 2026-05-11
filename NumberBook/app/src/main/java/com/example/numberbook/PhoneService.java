package com.example.numberbook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PhoneService {

    @GET("getAllContacts.php")
    Call<List<PersonData>> fetchStoredNumbers();

    @POST("insertContact.php")
    Call<ServerReply> saveNumber(@Body PersonData personData);

    @GET("searchContact.php")
    Call<List<PersonData>> findNumber(@Query("keyword") String searchText);
}