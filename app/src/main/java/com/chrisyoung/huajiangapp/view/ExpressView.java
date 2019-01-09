package com.chrisyoung.huajiangapp.view;

import com.chrisyoung.huajiangapp.network.HttpResult;

public interface ExpressView extends BaseView {
    void updateView(HttpResult<String> result);
}
