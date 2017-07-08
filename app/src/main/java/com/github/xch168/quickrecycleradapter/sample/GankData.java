package com.github.xch168.quickrecycleradapter.sample;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by XuCanHui on 2017/7/8.
 */

public class GankData {

    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public List<Gank> gankList;

}
