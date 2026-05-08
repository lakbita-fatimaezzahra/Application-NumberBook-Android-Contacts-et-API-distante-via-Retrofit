package com.example.numberbook;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContactApi {
    @GET("getAllContacts.php")
    Call<List<Contact>> getAllContacts();

    @POST("insertContact.php")
    Call<ApiResponse> insertContact(@Body Contact contact);

    @GET("searchContact.php")
    Call<List<Contact>> searchContacts(@Query("keyword") String keyword);
}