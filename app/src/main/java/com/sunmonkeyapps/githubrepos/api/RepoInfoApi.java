package com.sunmonkeyapps.githubrepos.api;

import com.sunmonkeyapps.githubrepos.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RepoInfoApi {
    String BASE_URL = "https://api.github.com/";

    @GET("users/{login}/repos")
    Call<List<Repo>> getReposByName(@Path("login") String login);

}
