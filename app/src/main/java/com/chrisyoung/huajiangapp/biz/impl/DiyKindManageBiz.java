package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IDiyKindManageBiz;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.dao.IUserDiyKindDao;
import com.chrisyoung.huajiangapp.dao.impl.UserDiyKindDaoImpl;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;

public class DiyKindManageBiz implements IDiyKindManageBiz {
    private IUserDiyKindDao kindDao;

    public DiyKindManageBiz(){
        kindDao=new UserDiyKindDaoImpl();
    }
    @Override
    public boolean addKind(CUser user, CUserDiyKind newKind) {
        newKind.setdStatus(StatusCode.INSERT);
        newKind.setdVersion(1);
        return kindDao.addKindForUser(user,newKind);
    }

    @Override
    public boolean updateKind(CUserDiyKind kind) {
        int version=kind.getdVersion();
        version++;
        kind.setdVersion(version);
        kind.setdStatus(StatusCode.UPDATE);
        return kindDao.addOrUpdateKind(kind);
    }

    @Override
    public ArrayList<CUserDiyKind> showKind(String uId,String type) {
        return kindDao.showAllKinds(uId,type);
    }

    @Override
    public boolean deleteKind(String dId) {
        return kindDao.deleteKind(dId);
    }

    @Override
    public boolean fakeDeleteKind(String dId) {
        CUserDiyKind kind=kindDao.findAKind(dId);
        if(kind!=null){
            CUserDiyKind d=new CUserDiyKind();
            d.setdId(kind.getdId());
            d.setuId(kind.getuId());
            d.setdType(kind.getdType());
            d.setdKind(kind.getdKind());
            d.setdStatus(StatusCode.DELETE);
            d.setDelflag(1);
            return kindDao.addOrUpdateKind(d);
        }
        return false;

    }

    @Override
    public void closeRealm() {
        kindDao.closeRealm();
    }
}
