package com.chrisyoung.huajiangapp.network;



import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.Record;
import com.chrisyoung.huajiangapp.dto.AppUser;
import com.chrisyoung.huajiangapp.dto.UserDiy;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;
import com.chrisyoung.huajiangapp.dto.UserAuths;
import com.chrisyoung.huajiangapp.dto.VerificationCode;

import java.util.LinkedList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("usr/auth/login")
    Observable<HttpResult<String>> login(@Query("identify") String identify, @Query("credential") String credential);

    @POST("usr/auth/register")
    Observable<HttpResult<String>> register(@Body UserAuths auths);


    @POST("usr/modifyInfo")
    Observable<HttpResult<String>> modifyInfo(@Header("Authorization") String token,@Body AppUser userInfo);

    @GET("usr/modifyPwd")
    Observable<HttpResult<String>> modifPwd(@Header("Authorization") String token,@Query("identify") String identify,@Query("newPwd") String newPwd);

    @GET("usr/auth/getmsg")
    Observable<HttpResult<VerificationCode>> getCode(@Query("phone") String phone);

//    @POST("img/upload")
//    Observable<HttpResult<String>> uploadImage(@Header("Authorization") String token, @Multipart("image") )

    @GET("sychdata/userInfos2c")
    Observable<HttpResult<AppUser>> sychronizeUserInfoS2C(@Header("Authorization") String token, @Query("UserId") String uId);

    @GET("sychdata/bills2c")
    Observable<HttpResult<LinkedList<SychronizeDataItem<Bill>>>> sychronizeBillS2C(@Header("Authorization") String token, @Query("UserId") String uId);

    @GET("sychdata/records2c")
    Observable<HttpResult<LinkedList<SychronizeDataItem<Record>>>> sychronizeRecordsS2C(@Header("Authorization") String token, @Query("bId") String bId);

    @GET("sychdata/diykindss2c")
    Observable<HttpResult<LinkedList<SychronizeDataItem<UserDiy>>>> sychronizeDiyKindsS2C(@Header("Authorization") String token, @Query("UserId") String uId);

    @POST("sychdata/billsc2s")
    Observable<HttpResult<String>> sychronizeBillsC2S(@Header("Authorization") String token, @Body LinkedList<SychronizeDataItem<Bill>> bills);

    @POST("sychdata/recordsc2s")
    Observable<HttpResult<String>> sychronizeRecordsC2S(@Header("Authorization") String token,@Body LinkedList<SychronizeDataItem<Record>> records);

    @POST("sychdata/diykindsc2s")
    Observable<HttpResult<String>> sychronizeDiyKindsC2S(@Header("Authorization") String token,@Body LinkedList<SychronizeDataItem<UserDiy>> kinds);

    @GET("usr/getuid")
    Observable<HttpResult<AppUser>> getUid(@Header("Authorization") String token);






}
