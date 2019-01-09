package com.chrisyoung.huajiangapp.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class RegistPresenter extends BasePresenter{
    public RegistPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);
    }
}
