package com.chrisyoung.huajiangapp.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class ChartPresenter extends BasePresenter {
    public ChartPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);
    }
}
