package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.network.HttpResult;

public interface ILoginView extends BaseView {
    void jump2MainActivity(String parm);

    void showResult(HttpResult<String> result);

}
