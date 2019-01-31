package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;

public interface IDiyKindManageBiz {
    boolean addKind(String uId, CUserDiyKind newKind);

    boolean updateKind(CUserDiyKind kind);

    ArrayList<CUserDiyKind> showKind(String uId);

    boolean deleteKind(String dId);
}
