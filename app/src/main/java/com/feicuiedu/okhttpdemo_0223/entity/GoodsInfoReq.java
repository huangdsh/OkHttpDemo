package com.feicuiedu.okhttpdemo_0223.entity;

import com.google.gson.annotations.SerializedName;

// 商品详情请求体
public class GoodsInfoReq{

    // json字符串里面的name和实体类定义的属性名称不一样，所以要进行序列化名称
    @SerializedName("goods_id")
    private int mGoodsId;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }
}