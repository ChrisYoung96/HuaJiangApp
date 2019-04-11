package com.chrisyoung.huajiangapp.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static ApiService service;
    //请求超时时间
    private static final int DEFAULT_TIMEOUT=10000;

    public static ApiService getDefault(){
        if(service==null){
            OkHttpClient.Builder httpClientBuilder=new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);

            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request request=chain.request();

                    HttpUrl.Builder authorizedUrlBuilder=request.url()
                            .newBuilder();
                    Request newRequest=request.newBuilder()
                            .method(request.method(),request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    return chain.proceed(newRequest);

                }
            });

            service=new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //.baseUrl("https://huajiangzhangben.xin:8443/appserver/")
                    .baseUrl("http://10.0.2.2:8080/")
                    .build().create(ApiService.class);
        }
        return service;
    }

}
