package com.sunmonkeyapps.githubrepos.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sunmonkeyapps.githubrepos.R;
import com.sunmonkeyapps.githubrepos.model.Repo;
import com.sunmonkeyapps.githubrepos.model.RepoHeader;
import com.sunmonkeyapps.githubrepos.utils.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import butterknife.BindView;

/**
 *
 *  This adapter takes care of Repo header ViewHolder and Repo item ViewHolder.
 *  Create the card view to represent the RecyclerView item.
 *  The card view can be RecyclerView header or repo item.
 */

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RepoAdapter";

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    RepoHeader mRepoHeader;
    Repo mRepo;
    static List<Repo> listRepos;
    static Context mContext;

    public RepoAdapter(Context context, RepoHeader repoHeader, List<Repo> listRepos) {
        this.listRepos = listRepos;
        this.mRepoHeader = repoHeader;
        mContext = context;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return either RepoHeaderViewHold or RepoViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_header_item, parent, false);
            return  new RepoHeaderViewHolder(view);

        } else if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
            return new RepoViewHolder(view);
        }

        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    /**
     *
     * @param holder
     * @param position
     *
     * The holder could be RepoHeaderViewholder or RepoViewHolder.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof RepoHeaderViewHolder) {
            RepoHeaderViewHolder headerItem = (RepoHeaderViewHolder) holder;
            headerItem.tvHeaderShortDesc.setText(mRepoHeader.getHeaderDescription());
//            headerItem.tvHeaderTitle.setText(mContext.getString(R.id.text_header_title));
        }
        else if(holder instanceof RepoViewHolder) {

            // populate the data to view
            onBindRepoItemViewHolder(holder, position);

            ((RepoViewHolder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Repo repo= new Repo();
                    repo= listRepos.get(position -1);
                    String cloneUrl = repo.getCloneUrl();
                    // open clone url in a broswer
                    openBrowser(cloneUrl);

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        // we need to add 1 to item count because we need to include the RepoHeader item
        return listRepos.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)){
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    /**
     *
     * @param holder
     * @param position
     *
     *  This method populate data from Repo model to TextView, ImageView.
     *  Glide lib is used to load the avatar image.
     *  Call utils/Utility.formatRepoDescription() to format the repo description.
     */
    private void onBindRepoItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        mRepo = listRepos.get(position -1);

        RepoViewHolder repoItem = (RepoViewHolder)holder;
        repoItem.tvName.setText(mRepo.getName());
        repoItem.tvId.setText(mRepo.getId());
        repoItem.tvLanguage.setText(mRepo.getLanguage());
        repoItem.tvUpdatedAt.setText(mRepo.getUpdatedAt());

        // Call Utility class to format the repo description -
        repoItem.tvDescription.setText(Utility.formatRepoDescription(mContext, mRepo.getDescription()));

        repoItem.tvLogin.setText(mRepo.getOwner().getLogin());

        // Load the repo user' avatar
        String mAvatarUrl = mRepo.getOwner().getAvatarUrl();
        if (!TextUtils.isEmpty(mAvatarUrl)) {
            Glide.with(mContext)
                    .load(mAvatarUrl)
                    .into(((RepoViewHolder) holder).ivAvatar);
        }

    }

    /**
     *
     * @param cloneUrl
     *
     * This method starts a activity to open the repo clone url in a browser.
     * If no browser is available, toast a message to alert the user.
     */
    private void openBrowser(String cloneUrl) {
        Log.d(TAG, "openBrowser: ");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cloneUrl));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (browserIntent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(browserIntent);
        } else {
            Log.d(TAG, "No Intent available to handle action");
            Toast.makeText(mContext,"Unable to Open a browse", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     *  ViewHolder named RepoHeaderViewHolder for repo header
     */
    public static class RepoHeaderViewHolder extends RecyclerView.ViewHolder {

        // Define the bindings to the ViewHolder's views
        @Nullable @BindView(R.id.tvHeaderTitle) TextView tvHeaderTitle;
        @Nullable @BindView(R.id.tvHeaderShortDesc) TextView tvHeaderShortDesc;

        public RepoHeaderViewHolder(View headerView) {
            super(headerView);
            // Bind ButterKnife to this view holder
            ButterKnife.bind(this, headerView);
        }
    }
    /**
     * ViewHolder named RepoViewHolder for user repos
     */
    public static class RepoViewHolder  extends RecyclerView.ViewHolder {

        View mCardView;
        // Define the bindings to the ViewHolder's views
        @Nullable @BindView(R.id.cvRepoItem) CardView cvRepoItem;

        @Nullable @BindView(R.id.tvName) TextView tvName;
        @Nullable @BindView(R.id.ivAvatar) ImageView ivAvatar;
        @Nullable @BindView(R.id.tvId) TextView tvId;
        @Nullable @BindView(R.id.tvLogin) TextView tvLogin;
        @Nullable @BindView(R.id.tvLanguage) TextView tvLanguage;
        @Nullable @BindView(R.id.tvUpdatedAt) TextView tvUpdatedAt;
        @Nullable @BindView(R.id.tvDescription) TextView tvDescription;


        public RepoViewHolder(View view) {
            super(view);
            // Bind ButterKnife to this view holder
            ButterKnife.bind(this, view);
            mCardView = view;
        }

    }
}
