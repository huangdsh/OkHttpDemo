package com.feicuiedu.okhttpdemo_0223.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 商品详情实体.
 */
public class GoodsInfo {

    @SerializedName("id") private int mId;

    @SerializedName("goods_name") private String mName;

    @SerializedName("shop_price") private String mShopPrice;

    @SerializedName("market_price") private String mMarketPrice;

    @SerializedName("goods_number") private int mNumber;

    @SerializedName("collected") private int mCollected;

    public String getMarketPrice() {
        return mMarketPrice;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getShopPrice() {
        return mShopPrice;
    }

    public int getNumber() {
        return mNumber;
    }

    public boolean isCollected() {
        return mCollected == 1;
    }
}
