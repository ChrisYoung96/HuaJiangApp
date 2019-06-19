package com.chrisyoung.huajiangapp.presenter;

import android.util.Log;

import com.chrisyoung.huajiangapp.biz.ISyncDataBiz;
import com.chrisyoung.huajiangapp.biz.impl.DiyKindManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.SyncDataBiz;
import com.chrisyoung.huajiangapp.constant.ResultCode;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;
import com.chrisyoung.huajiangapp.dto.UserDiy;
import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.EntityConvertUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IKindView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.LinkedList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class KindPresenter extends BasePresenter {
    private DiyKindManageBiz kindManageBiz;
    private IKindView kindView;
    private IUserDao userDao;
    private DataManager dataManager;
    private ISyncDataBiz syncDataBiz;


    public KindPresenter(LifecycleProvider<ActivityEvent> provider,IKindView kindView) {
        super(provider);
        this.kindManageBiz = new DiyKindManageBiz();
        this.kindView=kindView;
        userDao=new UserDaoImpl();
        dataManager=DataManager.getInstance();

    }


    public void showKinds(String uId,String type){
        ArrayList<CUserDiyKind> kinds=kindManageBiz.showKind(uId,type);
        kindView.showKinds(kinds);
    }

    public boolean deleteKind(String cId){
       return  kindManageBiz.fakeDeleteKind(cId);
    }

    public void addKind(CUserDiyKind kind){
        CUser user=userDao.showUserInfo(kind.getuId());
        if(kindManageBiz.addKind(user,kind)){
            kindView.showResult("添加成功");
        }else{
            kindView.showResult("添加失败");
        }

    }

    public void synchKindDataFromServer(String token,String uId){
        syncDataBiz=new SyncDataBiz();
        dataManager.sycnchronizedKindsS2C(token, uId)
                .subscribeOn(Schedulers.io())
                .map(new Function<HttpResult<LinkedList<SychronizeDataItem<UserDiy>>>, LinkedList<SychronizeDataItem<CUserDiyKind>>>() {
                    @Override
                    public LinkedList<SychronizeDataItem<CUserDiyKind>> apply(HttpResult<LinkedList<SychronizeDataItem<UserDiy>>> linkedListHttpResult) throws Exception {
                        LinkedList<SychronizeDataItem<UserDiy>> sKinds=linkedListHttpResult.getData();
                        LinkedList<SychronizeDataItem<CUserDiyKind>> cKinds=new LinkedList<>();
                        if(linkedListHttpResult.getCode().equals(ResultCode.SUCCESS.code())){
                            if(sKinds!=null && ! sKinds.isEmpty()){
                                while (!sKinds.isEmpty()){
                                    SychronizeDataItem<UserDiy> kind=sKinds.poll();
                                    if(kind.getData()!=null){
                                        CUserDiyKind cKind=EntityConvertUtil.SUserDiyKind2CUserDiyKind(kind.getData());
                                        if(cKind!=null){
                                            SychronizeDataItem<CUserDiyKind> diyKindSychronizeDataItem=new SychronizeDataItem<>();
                                            diyKindSychronizeDataItem.setData(cKind);
                                            diyKindSychronizeDataItem.setOptCode(StatusCode.INSERT);
                                            cKinds.add(diyKindSychronizeDataItem);
                                        }
                                    }
                                }
                            }
                        }
                        return cKinds;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().bindToLifecycle())
                .subscribe(new Consumer<LinkedList<SychronizeDataItem<CUserDiyKind>>>() {
                    @Override
                    public void accept(LinkedList<SychronizeDataItem<CUserDiyKind>> sychronizeDataItems) throws Exception {
                        if(syncDataBiz.synchronizeKindS2C(sychronizeDataItems)){
                            kindView.refreshUI();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "KindError: ", throwable);
                    }
                });

    }

    public void closeRealm(){
        kindManageBiz.closeRealm();
    }
}
