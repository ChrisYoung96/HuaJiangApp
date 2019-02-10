package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.view.vinterface.BaseView;

public interface ExpressView extends BaseView {
    void updateView(HttpResult<String> result);
}
