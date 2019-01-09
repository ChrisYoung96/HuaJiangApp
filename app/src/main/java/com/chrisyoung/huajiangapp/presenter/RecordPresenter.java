package com.chrisyoung.huajiangapp.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class RecordPresenter extends BasePresenter {
    public RecordPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);
    }
}
