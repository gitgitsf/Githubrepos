package com.sunmonkeyapps.githubrepos.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sunmonkeyapps.githubrepos.R;
import com.sunmonkeyapps.githubrepos.adapter.RepoAdapter;
import com.sunmonkeyapps.githubrepos.api.RepoInfoApi;
import com.sunmonkeyapps.githubrepos.model.Repo;
import com.sunmonkeyapps.githubrepos.model.RepoHeader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This app shows all repo belongs Ocramius from https://api.github.com/users/ocramius/repos
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public String REPO_LOGIN = "Ocramius";
    public final String FULL_API_URL ="https://api.github.com/users/ocramius/repos";

    RecyclerView rvGithubRepos;
    RepoAdapter adapter;

    Context mContext;
    List<Repo> mRepoList;

    RepoHeader mRepoHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        setupView();
        setupRepoHeaderData();

        getGithubUserRepos();

    }

    /**
     *  Retrieve the header description from strings.xml, replace the word "LOGIN" with the real
     * login and append the url that is used to retrieve the repo data
     */
    private void setupRepoHeaderData() {

        String headerDesc = getResources().getString(R.string.text_header_description);
        headerDesc = headerDesc.replace("LOGIN", REPO_LOGIN);

        headerDesc += FULL_API_URL;
        mRepoHeader = new RepoHeader();
        mRepoHeader.setHeaderDescription(headerDesc);

    }
    private RepoHeader getRepoHeader() {
        return mRepoHeader;
    }

    /**
     * Use Retrofit to retrieve the user repo over the network and have Gson converts the JSON data
     * into Repo model.
     * Call RepoAdapter and pass the Context, the RepoHeader and List of Repos as params
     */
    private void getGithubUserRepos() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RepoInfoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        final RepoInfoApi mRepoInfoApi = retrofit.create(RepoInfoApi.class);

        // Create a call instance to get all repos for a user
        Call<List<Repo>> call = mRepoInfoApi.getReposByName(REPO_LOGIN);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                mRepoList = response.body();
                Log.d(TAG, "onResponse: " + mRepoList.get(0).getId());

                adapter = new RepoAdapter(mContext, getRepoHeader(), mRepoList);
                rvGithubRepos.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage().toString());

            }
        });

    }

    private void setupView() {

        rvGithubRepos = (RecyclerView) findViewById(R.id.rvGithubRepos);
        rvGithubRepos.setHasFixedSize(true); // improve performance

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvGithubRepos.setLayoutManager(mLayoutManager);
        rvGithubRepos.setItemAnimator(new DefaultItemAnimator());

    }

}
