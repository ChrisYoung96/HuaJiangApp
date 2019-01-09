package com.chrisyoung.huajiangapp.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class UserInfoPresenter extends BasePresenter {
    public UserInfoPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);
    }
}
