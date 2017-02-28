package com.feicuiedu.okhttpdemo_0223.network;

import com.feicuiedu.okhttpdemo_0223.entity.GoodsInfoReq;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by gqq on 2017/2/23.
 */

// 单例类：OkHttpClient做单例化
public class NetClient {


    private static final String BASE_URL = "http://106.14.32.204/eshop/emobile/?url=";

    private static NetClient mNetClient;
    private final OkHttpClient mOkHttpClient;

    // 私有的构造方法
    private NetClient() {
        // 完成OkHttpClient的初始化

        // ctrl+alt+F 提取成全局变量
        // 日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // 默认是NONE，没有信息，所以我们自己给他设置打印级别
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

    }

    // 公有的创建方法
    public static synchronized NetClient getInstance(){
        if (mNetClient==null){
            mNetClient = new NetClient();
        }
        return mNetClient;
    }


    // 请求信息的接口构建，请求的响应解析，使用Gson解析
    public Call getInfo(){

        // 一般可以上传实体类的Json字符串格式。
//        RequestBody requestBody = RequestBody.create(null,json);

        // 键值对形式：表单的形式，以表单形式提交
        GoodsInfoReq goodsInfoReq = new GoodsInfoReq();
        goodsInfoReq.setGoodsId(78);
        String json = new Gson().toJson(goodsInfoReq);

        RequestBody requestBody = new FormBody.Builder()
                .add("json",json)
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL+"/goods")
                .build();
        return mOkHttpClient.newCall(request);
    }
}
