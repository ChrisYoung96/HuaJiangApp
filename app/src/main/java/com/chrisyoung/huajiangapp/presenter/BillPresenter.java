package com.chrisyoung.huajiangapp.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class BillPresenter extends BasePresenter {
    public BillPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);
    }
}
