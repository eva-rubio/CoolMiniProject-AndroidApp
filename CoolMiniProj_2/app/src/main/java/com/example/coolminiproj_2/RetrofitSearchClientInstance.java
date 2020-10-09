package com.example.coolminiproj_2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// http://openlibrary.org/search.json?q=the+lord+of+the+rings
// http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=" + apiKey
//@GET("/lists/movies/box_office.json")

// to issue network requests to a REST API w Retrofit

/*
* Retrofit Class:
* Retrofit adapts a Java interface to HTTP calls by using annotations
* on the declared methods to define how requests are made.
* Create instances using the builder and pass your interface
* to create(java.lang.Class<T>) to generate an implementation.
*
* */


public class RetrofitSearchClientInstance {

    private static Retrofit retrofit;
    private static final String SEARCH_BASE_URL = "http://openlibrary.org/";

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(SEARCH_BASE_URL)          //Set the API base URL.
                    .addConverterFactory(GsonConverterFactory.create())     //Add converter factory for serialization and deserialization of objects.
                    .build();
        }

        return retrofit;
    }

}
