package com.chrisyoung.huajiangapp.dao;

import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;

public interface IUserDiyKindDao {
    boolean addOrUpdateKind(CUserDiyKind newKind);

    boolean addKindForUser(CUser user, CUserDiyKind newKind);

    boolean deleteKind(String kId);

    CUserDiyKind findAKind(String kId);


    ArrayList<CUserDiyKind> showAllKinds(String uId,String type);

    ArrayList<CUserDiyKind> showKindsNeedSynchronize(String uId);

    void closeRealm();

}
