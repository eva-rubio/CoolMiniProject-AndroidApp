package com.example.coolminiproj_2;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// http://openlibrary.org/search.json?q=the+lord+of+the+rings

//http://openlibrary.org/search.json?q=eva

public interface SearchOpenLibraryService {

    @GET("/search.json")
    Call<QueryBookFullResponse> getFullSearch(@Query("q") String userSearchInput);
}
