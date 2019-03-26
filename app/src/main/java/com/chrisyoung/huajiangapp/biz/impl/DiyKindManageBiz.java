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
    public boolean fakeDeleteKind(CUserDiyKind kind) {
        kind.setdStatus(StatusCode.DELETE);
        return kindDao.addOrUpdateKind(kind);
    }
}
