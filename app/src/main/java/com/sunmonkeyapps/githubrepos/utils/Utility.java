package com.sunmonkeyapps.githubrepos.utils;


import android.content.Context;

import com.sunmonkeyapps.githubrepos.R;

import static android.text.TextUtils.isEmpty;

public class Utility {

    public static String formatRepoDescription(Context context, String repoDesc) {

        String str;
        if (isEmpty(repoDesc)) {
            str = context.getResources().getString(R.string.no_repo_description_msg);
        } else {
            str = context.getResources().getString(R.string.repo_description_msg_prefix) + repoDesc;

        }

        return str;
    }
}
