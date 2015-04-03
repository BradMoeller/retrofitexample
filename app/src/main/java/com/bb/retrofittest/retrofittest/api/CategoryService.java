package com.bb.retrofittest.retrofittest.api;

import com.bb.retrofittest.retrofittest.model.MCategoryPage;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Brad on 3/31/2015.
 */
public interface CategoryService {

    @GET("/")
    void listCategories(Callback<MCategoryPage> callback);

    @GET("/categories/{id}")
    void listCategories(@Path("id") String categoryId, Callback<MCategoryPage> callback);

    // THESE DON'T WORK!
    // '/' gets encoded to '%2F'
    // must start with '/' or retrofit throws exception
    @GET("{route}")
    void genericGet(@Path("route") String route, Callback<MCategoryPage> callback);
}
