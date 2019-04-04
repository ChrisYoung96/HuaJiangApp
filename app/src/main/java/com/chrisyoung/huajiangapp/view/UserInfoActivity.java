package com.chrisyoung.huajiangapp.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.InputType;
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
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity implements IMineInfoView, OnDateSetListener {
    private String uId;
    private UserInfoPresenter presenter;


    private String photoPath = "";

    ImageView imageView;

    File imgFile;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    TimePickerDialog mDialogYearMonth;

    @BindView(R.id.btnMineBack)
    ImageButton btnMineBack;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.infoGroupListView)
    QMUIGroupListView infoGroupListView;

    QMUICommonListItemView headPhoto;

    QMUICommonListItemView myName;

    QMUICommonListItemView mySex;

    QMUICommonListItemView myBirthday;

    QMUICommonListItemView myPhone;

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
        photoPath = ImageUtil.readpic(ImageUtil.IMAGE_FILE_FOLDER + ImageUtil.HEAD_PHOTP_PREFIX + uId + ImageUtil.IMAGE_SUFFIX);
        imgFile = new File(photoPath);
        headPhoto = infoGroupListView.createItemView(
                "头像");
        headPhoto.setOrientation(QMUICommonListItemView.VERTICAL);
        headPhoto.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        imageView = new ImageView(this);
        if (photoPath.equals("")) {
            imageView.setImageResource(R.mipmap.account);
        } else {
            Uri uri = FileProvider.getUriForFile(UserInfoActivity.this, ImageUtil.FILE_PROVIDER, imgFile);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        headPhoto.addAccessoryCustomView(imageView);


        myName = infoGroupListView.createItemView(getResources().getText(R.string.nicheng));
        myName.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myName.setDetailText(user.getuName());
        myName.setOrientation(QMUICommonListItemView.HORIZONTAL);

        mySex = infoGroupListView.createItemView(getResources().getText(R.string.xingbie));
        mySex.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        mySex.setDetailText(user.getuSex());


        myBirthday = infoGroupListView.createItemView(getResources().getText(R.string.shengri));
        myBirthday.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myBirthday.setDetailText(DateFormatUtil.dateToString(user.getuBirthday()));
        myBirthday.setOrientation(QMUICommonListItemView.HORIZONTAL);

        myPhone = infoGroupListView.createItemView(getResources().getText(R.string.dianhua));
        myPhone.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myPhone.setDetailText(user.getuPhone());
        myPhone.setOrientation(QMUICommonListItemView.HORIZONTAL);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if (text.equals(getResources().getText(R.string.touxiang))) {
                        UpdatePhoto(v);
                    } else if (text.equals(getResources().getText(R.string.nicheng))) {
                        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(UserInfoActivity.this);
                        builder.setTitle("修改昵称")
                                .setPlaceholder("在此输入您的昵称")
                                .setInputType(InputType.TYPE_CLASS_TEXT)
                                .addAction("取消", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                })
                                .addAction("确定", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        CharSequence text = builder.getEditText().getText();
                                        if (text != null && text.length() > 0) {
                                            CUser cUser=new CUser();
                                            cUser.setuId(uId);
                                            cUser.setuName(text.toString());
                                            cUser.setuBirthday(DateFormatUtil.stringToDate(myBirthday.getDetailText().toString()));
                                            cUser.setuSex(mySex.getDetailText().toString());
                                            cUser.setuPhone(myPhone.getDetailText().toString());
                                            cUser.setuPhoto(photoPath);
                                            presenter.updateUserInfo(cUser);
                                            dialog.dismiss();
                                            presenter.initView(uId);
                                        } else {
                                            ToastUtil.showShort(UserInfoActivity.this, "请输入昵称");
                                        }
                                    }
                                })
                                .create(mCurrentDialogStyle).show();
                    } else if (text.equals(getResources().getText(R.string.shengri))) {
                        initDatePicker();
                    }else if(text.equals(getResources().getText(R.string.xingbie))){
                        final String[] items = new String[]{"男", "女"};
                        final int checkedIndex = 1;
                        new QMUIDialog.CheckableDialogBuilder(UserInfoActivity.this)
                                .setCheckedIndex(checkedIndex)
                                .addItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        CUser cUser=new CUser();
                                        cUser.setuId(uId);
                                        cUser.setuName(myName.getDetailText().toString());
                                        cUser.setuBirthday(DateFormatUtil.stringToDate(myBirthday.getDetailText().toString()));
                                        cUser.setuSex(items[which]);
                                        cUser.setuPhone(myPhone.getDetailText().toString());
                                        cUser.setuPhoto(photoPath);
                                        presenter.updateUserInfo(cUser);
                                        dialog.dismiss();
                                        presenter.initView(uId);
                                    }
                                })
                                .create(mCurrentDialogStyle).show();
                    }
                }
            }
        };


        infoGroupListView.removeAllViews();


       QMUIGroupListView.Section section= QMUIGroupListView.newSection(this);
       section.addItemView(headPhoto, onClickListener)
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
                        ImageUtil.takePhoto(UserInfoActivity.this, imgFile, ImageUtil.HEAD_PHOTP_PREFIX + uId + ImageUtil.IMAGE_SUFFIX);
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
                    ImageUtil.takePhoto(UserInfoActivity.this, imgFile, ImageUtil.HEAD_PHOTP_PREFIX + uId + ImageUtil.IMAGE_SUFFIX);
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
            ImageUtil.startPhotoZoom(uri, 192, 192, imgFile, UserInfoActivity.this);

        } else if (requestCode == ImageUtil.REQUEST_GALLARY && resultCode == Activity.RESULT_OK
                && null != data) {
            imgFile = new File(photoPath);
            Uri selectedImage = data.getData();
            ImageUtil.startPhotoZoom(selectedImage, 192, 192, imgFile, UserInfoActivity.this);

        } else if (requestCode == ImageUtil.REQUEST_CROP_PHOTO && data != null && resultCode == Activity.RESULT_OK) {

            Uri uri = FileProvider.getUriForFile(UserInfoActivity.this, ImageUtil.FILE_PROVIDER, imgFile);

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                ImageUtil.savePhotoToSdCard(bitmap, photoPath);
                saveImageToServer(bitmap, photoPath);
                //把图片加入图库
                ImageUtil.galleryAddPic(photoPath, this);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }


    private void saveImageToServer(final Bitmap bitmap, String outfile) {


        ToastUtil.showLong(this, outfile);
        // TODO: 2018/12/4  这里就可以将图片文件 file 上传到服务器,上传成功后可以将bitmap设置给你对应的图片展示
        imageView.setImageBitmap(bitmap);

        ImageUtil.galleryAddPic(outfile, this);
    }


    private void initDatePicker() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setThemeColor(this.getColor(R.color.titleBackground))
                .setTitleStringId("选择日期")
                .setCallBack(this)
                .setWheelItemTextNormalColorId(this.getColor(R.color.timepickerbackground))
                .build();
        assert this.getFragmentManager() != null;
        mDialogYearMonth.show(getSupportFragmentManager(), "year_month_day");
    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        CUser cUser=new CUser();
        cUser.setuId(uId);
        cUser.setuName(myName.getDetailText().toString());
        cUser.setuBirthday(new Date(millseconds));
        cUser.setuSex(mySex.getDetailText().toString());
        cUser.setuPhone(myPhone.getDetailText().toString());
        cUser.setuPhoto(photoPath);
        presenter.updateUserInfo(cUser);
        presenter.initView(uId);

    }
}
