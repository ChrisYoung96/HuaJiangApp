package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;

public  interface IKindView extends BaseInternetView {
    void showKinds(ArrayList<CUserDiyKind> kinds);

    void refreshUI();
}
