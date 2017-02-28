package com.feicuiedu.okhttpdemo_0223.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gqq on 17/2/19.
 */

// 商品详情的响应体
public class GoodsInfoRsp {

    @SerializedName("status")
    private Status mStatus;

    @SerializedName("data")
    private GoodsInfo mData;

    public GoodsInfo getData() {
        return mData;
    }

    public Status getStatus() {
        return mStatus;
    }
}
