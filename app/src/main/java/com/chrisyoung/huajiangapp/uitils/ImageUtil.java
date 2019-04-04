package com.chrisyoung.huajiangapp.uitils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.chrisyoung.huajiangapp.view.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    public static final String IMAGE_FILE_FOLDER="/huajiangzhangben/";

    public static final String IMAGE_SUFFIX=".png";

    public static final String HEAD_PHOTP_PREFIX="headPhoto";

    public static final String FILE_PROVIDER="com.chrisyoung.huajiangapp.fileprovider";
    //调取系统摄像头的请求码
    public  static final int MY_ADD_CASE_CALL_PHONE = 6;
    //打开相册的请求码
    public static final int MY_ADD_CASE_CALL_PHONE2 = 7;

    public static final int REQUEST_CAMERA = 1;

    public static final int REQUEST_GALLARY = 2;

    public static final int REQUEST_CROP_PHOTO = 3;

    // 在sd卡中创建一保存图片（原图和缩略图共用的）文件夹
    public static File createFileIfNeed(String fileName) throws IOException {
        String fileA = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/huajiangzhangben";
        File fileJA = new File(fileA);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File file = new File(fileA, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }


    /**
     * 把图片保存到SD卡
     * @param bitmap
     * @param targetPath
     */
    public static void savePhotoToSdCard(Bitmap bitmap, String targetPath) {

        FileOutputStream fileOutputStream = null;
        File file = new File(targetPath);
        try {
            fileOutputStream = new FileOutputStream(file);
            if (bitmap != null) {
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                        fileOutputStream)) {
                    fileOutputStream.flush();
                }
            }
        } catch (FileNotFoundException e) {
            file.delete();
            e.printStackTrace();
        } catch (IOException e) {
            file.delete();
            e.printStackTrace();
        } finally {
            try {
                // 到最后一定要关闭
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static void galleryAddPic(String photoPath,Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);//
        Uri contentUri = FileProvider.getUriForFile(context, "com.chrisyoung.huajiangapp.fileprovider", f);
        mediaScanIntent.setData(contentUri);  //设置URI
        context.sendBroadcast(mediaScanIntent);  //发送广播
    }

    /**
     * 裁剪照片
     */
    public static void startPhotoZoom(Uri uri, int width, int height, File file, BaseActivity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", width / height);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        // 图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:返回uri，false：不返回uri
        // 同一个地址下 裁剪的图片覆盖之前得到的图片
        Uri uri1 = getImageContentUri(activity, file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
        activity.startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    /**
     * 从保存原图的地址读取图片
     */
    public static String readpic(String path) {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + path;
        return filePath;
    }

    /**
     * 打开相册
     */
    public static void choosePhoto(BaseActivity activity) {
        //这是打开系统默认的相册(就是你系统怎么分类,就怎么显示,首先展示分类列表)
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(picture, REQUEST_GALLARY);
    }

    public static void takePhoto(BaseActivity activity,File imgFile,String fileName) throws IOException {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 获取文件
        imgFile = ImageUtil.createFileIfNeed(fileName);
        //拍照后原图回存入此路径下
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(imgFile);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(activity, FILE_PROVIDER, imgFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }
}
