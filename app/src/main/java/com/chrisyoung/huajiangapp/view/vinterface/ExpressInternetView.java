package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.network.HttpResult;

public interface ExpressInternetView extends BaseInternetView {
    void updateView(HttpResult<String> result);
}
