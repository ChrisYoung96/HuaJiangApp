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

public class DataManager {
    private static DataManager dataManager;
    private ApiService service;

    public static DataManager getInstance() {
        return dataManager == null ? dataManager = new DataManager() : dataManager;
    }

    private DataManager() {
        service = RetrofitHelper.getDefault();
    }

    public Observable<HttpResult<String>> login(String identify, String credential) {
        return service.login(identify, credential);
    }

    public Observable<HttpResult<String>> register(UserAuths auths) {
        return service.register(auths);
    }

    public Observable<HttpResult<VerificationCode>> getCode(String phone) {
        return service.getCode(phone);
    }

    public Observable<HttpResult<String>> modifyInfo(String token, AppUser user) {
        return service.modifyInfo(token, user);
    }

    public Observable<HttpResult<String>> modifyPassword(String token, String identify, String newPwd) {
        return service.modifPwd(token, identify, newPwd);
    }

    public Observable<HttpResult<LinkedList<SychronizeDataItem<Bill>>>> synchronizeBillsS2C(String token, String uId) {
        return service.sychronizeBillS2C(token, uId);
    }

    public Observable<HttpResult<LinkedList<SychronizeDataItem<Record>>>> synchronizedRecordsS2C(String token, String bId) {
        return service.sychronizeRecordsS2C(token, bId);
    }

    public Observable<HttpResult<LinkedList<SychronizeDataItem<UserDiy>>>> sycnchronizedKindsS2C(String token, String uId) {
        return service.sychronizeDiyKindsS2C(token, uId);
    }

    public Observable<HttpResult<AppUser>> synchronizeUserInfoS2C(String token, String uId) {
        return service.sychronizeUserInfoS2C(token, uId);
    }

    public Observable<HttpResult<String>> synchronizeBillsC2S(String token, LinkedList<SychronizeDataItem<Bill>> bills) {
        return service.sychronizeBillsC2S(token, bills);
    }

    public Observable<HttpResult<String>> synchronizeRecordsC2S(String token, LinkedList<SychronizeDataItem<Record>> records) {
        return service.sychronizeRecordsC2S(token, records);
    }

    public Observable<HttpResult<String>> synchronizeKindsC2S(String token, LinkedList<SychronizeDataItem<UserDiy>> kinds) {
        return service.sychronizeDiyKindsC2S(token, kinds);
    }

    public Observable<HttpResult<AppUser>> getUid(String token){
        return service.getUid(token);
    }

}
