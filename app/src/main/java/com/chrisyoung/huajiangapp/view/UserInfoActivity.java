package com.chrisyoung.huajiangapp.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.presenter.UserInfoPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.ImageUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IMineInfoView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity implements IMineInfoView {
    private String uId;
    private UserInfoPresenter presenter;



    private String photoPath = "";

    ImageView imageView;

    File imgFile;

    @BindView(R.id.btnMineBack)
    ImageButton btnMineBack;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.infoGroupListView)
    QMUIGroupListView infoGroupListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        uId = getIntent().getStringExtra("uId");
        presenter = new UserInfoPresenter(this, this);
        presenter.initView(uId);
    }


    @Override
    public void initView(CUser user) {
        if (user == null) {
            user = new CUser();
            user.setuPhone(" ");
            user.setuBirthday(DateFormatUtil.stringToDate("1970-1-1"));
            user.setuName(" ");
            user.setuSex("男");
            user.setuPhone(" ");
        }
        photoPath=ImageUtil.readpic(ImageUtil.IMAGE_FILE_FOLDER+ImageUtil.HEAD_PHOTP_PREFIX+uId+ImageUtil.IMAGE_SUFFIX);
        imgFile=new File(photoPath);
        QMUICommonListItemView headPhoto = infoGroupListView.createItemView(
                "头像");
        headPhoto.setOrientation(QMUICommonListItemView.VERTICAL);
        headPhoto.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        imageView = new ImageView(this);
        if(photoPath.equals("")){
            imageView.setImageResource(R.mipmap.account);
        }else{
            Uri uri = FileProvider.getUriForFile(UserInfoActivity.this, ImageUtil.FILE_PROVIDER, imgFile);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        headPhoto.addAccessoryCustomView(imageView);


        QMUICommonListItemView myName = infoGroupListView.createItemView("用户名");
        myName.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myName.setDetailText(user.getuName());
        myName.setOrientation(QMUICommonListItemView.HORIZONTAL);

        QMUICommonListItemView mySex = infoGroupListView.createItemView("性别");
        mySex.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        mySex.setDetailText(user.getuSex());


        QMUICommonListItemView myBirthday = infoGroupListView.createItemView("生日");
        myBirthday.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myBirthday.setDetailText(DateFormatUtil.dateToString(user.getuBirthday()));
        myBirthday.setOrientation(QMUICommonListItemView.HORIZONTAL);

        QMUICommonListItemView myPhone = infoGroupListView.createItemView("电话");
        myPhone.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myPhone.setDetailText(user.getuPhone());
        myPhone.setOrientation(QMUICommonListItemView.HORIZONTAL);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if (text.equals("头像")) {
                        UpdatePhoto(v);
                    }
                }
            }
        };


        QMUIGroupListView.newSection(this)
                .addItemView(headPhoto, onClickListener)
                .addItemView(myName, onClickListener)
                .addItemView(mySex, onClickListener)
                .addItemView(myBirthday, onClickListener)
                .addItemView(myPhone, onClickListener)
                .addTo(infoGroupListView);


        if (uId.equals("") || uId == null) {
            btnLogout.setText("登录");
        }
    }

    @Override
    public void jum2LoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showResult(String result) {

    }

    @OnClick({R.id.btnMineBack, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMineBack:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnLogout:
                if (uId.equals("") || uId == null) {
                    Intent intent1 = new Intent(this, LoginActivity.class);
                    startActivity(intent1);
                    fileList();
                } else {
                    presenter.logOut();
                }
                break;
        }
    }

    public void UpdatePhoto(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_select_photo, null);//获取自定义布局
        builder.setView(layout);
        final AlertDialog dlg = builder.create();
        Window window = dlg.getWindow();
        window.setGravity(Gravity.BOTTOM);
        //设置点击外围消散
        dlg.setCanceledOnTouchOutside(true);
        dlg.show();

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = dlg.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth(); //宽度设置为屏幕

        window.setBackgroundDrawable(new ColorDrawable(0));

        TextView button1 = layout.findViewById(R.id.photograph);
        TextView button2 = layout.findViewById(R.id.photo);
        TextView button3 = layout.findViewById(R.id.cancel);


        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //"点击了照相";
                //  6.0之后动态申请权限 摄像头调取权限,SD卡写入权限
                if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserInfoActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            ImageUtil.MY_ADD_CASE_CALL_PHONE);
                } else {
                    try {
                        //有权限,去打开摄像头
                        ImageUtil.takePhoto(UserInfoActivity.this,imgFile,ImageUtil.HEAD_PHOTP_PREFIX+uId+ImageUtil.IMAGE_SUFFIX);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dlg.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //"点击了相册");
                //  6.0之后动态申请权限 SD卡写入权限
                if (ContextCompat.checkSelfPermission(UserInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserInfoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            ImageUtil.MY_ADD_CASE_CALL_PHONE2);

                } else {
                    ImageUtil.choosePhoto(UserInfoActivity.this);
                }
                dlg.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dlg.dismiss();
            }
        });
    }



    /**
     * 申请权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == ImageUtil.MY_ADD_CASE_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    ImageUtil.takePhoto(UserInfoActivity.this,imgFile,ImageUtil.HEAD_PHOTP_PREFIX+uId+ImageUtil.IMAGE_SUFFIX);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //"权限拒绝");
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
            }
        }


        if (requestCode == ImageUtil.MY_ADD_CASE_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImageUtil.choosePhoto(UserInfoActivity.this);
            } else {
                //"权限拒绝");
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageUtil.REQUEST_CAMERA && resultCode != Activity.RESULT_CANCELED) {
            Uri uri = FileProvider.getUriForFile(UserInfoActivity.this, ImageUtil.FILE_PROVIDER, imgFile);
            ImageUtil.startPhotoZoom(uri, 192, 192, imgFile,UserInfoActivity.this);

        } else if (requestCode == ImageUtil.REQUEST_GALLARY && resultCode == Activity.RESULT_OK
                && null != data) {
            imgFile=new File(photoPath);
            Uri selectedImage = data.getData();
            ImageUtil.startPhotoZoom(selectedImage, 192, 192, imgFile,UserInfoActivity.this);

        } else if (requestCode ==ImageUtil. REQUEST_CROP_PHOTO && data != null && resultCode == Activity.RESULT_OK) {

            Uri uri = FileProvider.getUriForFile(UserInfoActivity.this, ImageUtil.FILE_PROVIDER, imgFile);

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                ImageUtil.savePhotoToSdCard(bitmap,photoPath);
                saveImageToServer(bitmap, photoPath);
                //把图片加入图库
                ImageUtil.galleryAddPic(photoPath,this);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }



    private void saveImageToServer(final Bitmap bitmap, String outfile) {


        ToastUtil.showLong(this, outfile);
        // TODO: 2018/12/4  这里就可以将图片文件 file 上传到服务器,上传成功后可以将bitmap设置给你对应的图片展示
        imageView.setImageBitmap(bitmap);

        ImageUtil.galleryAddPic(outfile,this);
    }




}
