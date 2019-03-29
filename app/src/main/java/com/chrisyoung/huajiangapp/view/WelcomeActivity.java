package com.chrisyoung.huajiangapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.iv_entry)
    ImageView mIVEntry;

    String token="";

    private static final int ANIM_TIME = 2000;

    private static final float SCALE_END = 1.15F;

    private static final int[] Imgs={
            R.drawable.welcomimg1,R.drawable.welcomimg2,
            R.drawable.welcomimg3,R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6,
            R.drawable.welcomimg7,R.drawable.welcomimg8,
            R.drawable.welcomimg9,R.drawable.welcomimg10,
            R.drawable.welcomimg11,R.drawable.welcomimg12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否是第一次开启应用
        boolean isFirstOpen =false;
        isFirstOpen=(boolean) SharedPreferenceUtil.get(this, "firstOpen", isFirstOpen);
        // 如果是第一次启动，则先进入功能引导页
        if (isFirstOpen) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        startMainActivity();
    }
    private void startMainActivity(){
        Random random = new Random(SystemClock.elapsedRealtime());//SystemClock.elapsedRealtime() 从开机到现在的毫秒数（手机睡眠(sleep)的时间也包括在内）
        mIVEntry.setImageResource(Imgs[random.nextInt(Imgs.length)]);

        token= (String) SharedPreferenceUtil.get(this,"token",token);

        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>()
                {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startAnim();
                    }
                });
    }

    private void startAnim() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIVEntry, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIVEntry, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {

                if(token==null||token.equals("")){
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
                WelcomeActivity.this.finish();
            }
        });
    }

    /**
     * 屏蔽物理返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
