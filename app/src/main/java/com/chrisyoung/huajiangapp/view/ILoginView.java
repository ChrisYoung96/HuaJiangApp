package com.chrisyoung.huajiangapp.view;

import com.chrisyoung.huajiangapp.network.HttpResult;

public interface ILoginView extends BaseView {
    void jump2MainActivity();

    void showResult(HttpResult<String> result);

}
