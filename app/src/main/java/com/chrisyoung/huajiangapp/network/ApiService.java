package com.chrisyoung.huajiangapp.network;



import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.dto.SBill;
import com.chrisyoung.huajiangapp.dto.SRecord;
import com.chrisyoung.huajiangapp.dto.SUser;
import com.chrisyoung.huajiangapp.dto.SUserDiy;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;
import com.chrisyoung.huajiangapp.dto.UserAuths;
import com.chrisyoung.huajiangapp.dto.VerificationCode;

import java.util.BitSet;
import java.util.LinkedList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("usr/auth/login")
    Observable<HttpResult<String>> login(@Query("identify") String identify, @Query("credential") String credential);

    @POST("usr/auth/register")
    Observable<HttpResult<String>> register(@Body UserAuths auths);


    @POST("usr/modifyInfo")
    Observable<HttpResult<String>> modifyInfo(@Header("Authorization") String token,@Body SUser userInfo);

    @GET("usr/modifyPwd")
    Observable<HttpResult<String>> modifPwd(@Header("Authorization") String token,@Query("identify") String identify,@Query("newPwd") String newPwd);

    @GET("usr/auth/getmsg")
    Observable<HttpResult<VerificationCode>> getCode(@Query("phone") String phone);

//    @POST("img/upload")
//    Observable<HttpResult<String>> uploadImage(@Header("Authorization") String token, @Multipart("image") )

    @GET("sychdata/userInfos2c")
    Observable<HttpResult<SUser>> sychronizeUserInfoS2C(@Header("Authorization") String token,@Query("UserId") String uId);

    @GET("sychdata/bills2c")
    Observable<HttpResult<LinkedList<SychronizeDataItem<SBill>>>> sychronizeBillS2C(@Header("Authorization") String token,@Query("UserId") String uId);

    @GET("sychdata/records2s")
    Observable<HttpResult<LinkedList<SychronizeDataItem<SRecord>>>> sychronizeRecordsS2C(@Header("Authorization") String token,@Query("bId") String bId);

    @GET("sychdata/diykindss2c")
    Observable<HttpResult<LinkedList<SychronizeDataItem<SUserDiy>>>> sychronizeDiyKindsS2C(@Header("Authorization") String token,@Query("UserId") String uId);

    @POST("sychdata/billsc2s")
    Observable<HttpResult<String>> sychronizeBillsC2s(@Header("Authorization") String token,@Body LinkedList<SychronizeDataItem<SBill>> bills);

    @POST("sychdata/recordsc2s")
    Observable<HttpResult<String>> sychronizeRecordsC2S(@Header("Authorization") String token,@Body LinkedList<SychronizeDataItem<SRecord>> records);

    @POST("sychdata/diykindsc2s")
    Observable<HttpResult<String>> sychronizeDiyKindsC2S(@Header("Authorization") String token,@Body LinkedList<SychronizeDataItem<SUserDiy>> kinds);






}
