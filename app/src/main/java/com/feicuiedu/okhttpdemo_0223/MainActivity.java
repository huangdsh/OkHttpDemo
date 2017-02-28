package com.feicuiedu.okhttpdemo_0223;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.feicuiedu.okhttpdemo_0223.entity.GoodsInfoRsp;
import com.feicuiedu.okhttpdemo_0223.network.NetClient;
import com.feicuiedu.okhttpdemo_0223.network.UICallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.okHttpGet)
    public void gotoNetwork(View view) {
        switch (view.getId()) {
            case R.id.okHttpGet:

                // 简单的构建一个GET请求
                /**
                 * 1. 创建一个客户端：目的是发送请求
                 * 2. 构建请求：GET
                 * 3. 发送请求给服务器
                 */

                // 日志拦截器
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                // 默认是NONE，没有信息，所以我们自己给他设置打印级别
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                mOkHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .addInterceptor(new ConnectInterceptor(mOkHttpClient))
                        .build();

                // 构造器模式创建请求
                final Request request = new Request.Builder()
                        // 请求方式:Get 不需要添加请求体
                        .get()

                        // 请求的url
                        .url("http://106.14.32.204/eshop/emobile/?url=/category")

                        // 不需要添加请求头，如果添加，服务器会当做无用资源，不做处理
                        .addHeader("zbc", "123")
                        .addHeader("123", "123")

                        // 给请求打标签
                        .tag(getClass().getSimpleName())

                        .build();

                // 同步
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try {
//                            Response response = okHttpClient.newCall(request).execute();
//
//                            response.code();// 响应码
//                            response.body();// 响应体
//                            response.header("123");// 获取响应头
//                            Headers headers = response.headers();
//
//                            if (response.isSuccessful()){
//                                Log.i("TAG","respone"+response.code());
//                            }
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();

                // 异步
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {

                    // 里面都是后台线程：不能做UI的操作
                    // 请求失败：网络未连接超时等
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("TAG", "onFailure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("TAG", "respone" + response.code());

                    }
                });

/*

                // 取出OkHttpClient构建的call:等待执行、正在执行

                // 1.等待执行的
                List<Call> calls = mOkHttpClient.dispatcher().queuedCalls();

                // 2. 正在执行
                List<Call> calls1 = mOkHttpClient.dispatcher().runningCalls();

                // 取消掉请求：Call
                call.cancel();

                // 取消某些call：构建Call的时候，给请求打个标签
*/

                break;

        }
    }

    @OnClick(R.id.okHttpPOST)
    public void getInfo(){
        Call call = NetClient.getInstance().getInfo();

//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("TAG","请求失败了"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()){
//                    String string = response.body().string();
//                    GoodsInfoRsp infoRsp = new Gson().fromJson(string, GoodsInfoRsp.class);
//                    Log.i("TAG","响应体的信息："+infoRsp.getStatus().isSucceed());
//                }
//            }
//        });

        call.enqueue(new UICallback() {

            // 这两个方法是运行在主线程的，可以更新UI
            @Override
            public void onFailureInUI(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "请求失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseInUI(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();
                    GoodsInfoRsp infoRsp = new Gson().fromJson(string, GoodsInfoRsp.class);
                    Toast.makeText(MainActivity.this, "成功"+infoRsp.getStatus().isSucceed(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 把页面上所有的请求都取消
        List<Call> calls = mOkHttpClient.dispatcher().queuedCalls();
        for (Call call :
                calls) {

            if (call.request().tag()==getClass().getSimpleName()) {

                call.cancel();
            }
        }

        List<Call> calls1 = mOkHttpClient.dispatcher().runningCalls();
        for (Call call :
                calls1) {

            // 根据标签取消单个或某些请求
            if (call.request().tag()==getClass().getSimpleName()) {

                call.cancel();
            }
        }
    }
}
