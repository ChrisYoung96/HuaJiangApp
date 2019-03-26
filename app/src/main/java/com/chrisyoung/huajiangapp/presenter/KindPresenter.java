package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.impl.DiyKindManageBiz;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.view.vinterface.IKindView;

import java.util.ArrayList;

public class KindPresenter {
    private DiyKindManageBiz kindManageBiz;
    private IKindView kindView;
    private IUserDao userDao;

    public KindPresenter(IKindView kindView) {
        this.kindManageBiz = new DiyKindManageBiz();
        this.kindView=kindView;
        userDao=new UserDaoImpl();
    }


    public void showKinds(String uId,String type){
        ArrayList<CUserDiyKind> kinds=kindManageBiz.showKind(uId,type);
        kindView.showKinds(kinds);
    }

    public void deleteKind(String cId){
        kindManageBiz.deleteKind(cId);
    }

    public void addKind(CUserDiyKind kind){
        CUser user=userDao.showUserInfo(kind.getuId());
        if(kindManageBiz.addKind(user,kind)){
            kindView.showResult("添加成功");
        }else{
            kindView.showResult("添加失败");
        }

    }
}
