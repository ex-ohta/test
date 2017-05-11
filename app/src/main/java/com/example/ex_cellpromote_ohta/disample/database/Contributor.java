package com.example.ex_cellpromote_ohta.disample.database;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ex-cellpromote-ohta on 2017/05/02.
 * Ormaで使用するentityクラス
 */

@SuppressWarnings("CheckStyle")
@Table
public class Contributor {

    @PrimaryKey(auto = false)
    @SerializedName("login")
    public String name;

    @Column
    @Nullable
    @SerializedName("avatar_url")
    public String avatarUrl;

    @Column
    @SerializedName("html_url")
    public String htmlUrl;

    @Column
    public int contributions;

}
