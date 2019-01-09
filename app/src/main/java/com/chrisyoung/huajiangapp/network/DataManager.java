package com.chrisyoung.huajiangapp.network;

import com.chrisyoung.huajiangapp.dto.SUser;
import com.chrisyoung.huajiangapp.dto.UserAuths;
import com.chrisyoung.huajiangapp.dto.VerificationCode;

import io.reactivex.Observable;

public class DataManager {
    private static DataManager dataManager;
    private ApiService service;

    public static  DataManager getInstance(){
        return dataManager == null?dataManager=new DataManager():dataManager;
    }

    private DataManager(){
        service=RetrofitHelper.getDefault();
    }

    public Observable<HttpResult<String>> login(String identify, String credential){
        return service.login(identify,credential);
    }

    public Observable<HttpResult<String>> register(UserAuths auths){
        return service.register(auths);
    }

    public Observable<HttpResult<VerificationCode>> getCode(String phone){
        return service.getCode(phone);
    }
}
