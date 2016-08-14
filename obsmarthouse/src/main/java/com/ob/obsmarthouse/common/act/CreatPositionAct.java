package com.ob.obsmarthouse.common.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ob.obsmarthouse.R;
import com.ob.obsmarthouse.common.base.BaseAct;
import com.ob.obsmarthouse.common.base.InteractiveBaseAct;
import com.ob.obsmarthouse.common.net.cloudnet.HttpRequst;
import com.ob.obsmarthouse.common.widget.TopTitle;

import org.apache.http.NameValuePair;

import java.io.File;
import java.util.List;

/**
 * 创建位置信息act,命名，背景选择
 * Created by adolf_dong on 2016/8/5.
 */
public class CreatPositionAct extends InteractiveBaseAct {
    private ImageButton creatBtn;
    private ImageView imageView;
    @Override
    protected void findView(Bundle savedInstanceState) {
        setContentView(R.layout.creatposition_act);
        creatBtn = (ImageButton) findViewById(R.id.create_position_btn);
        imageView = (ImageView) findViewById(R.id.create_position_img);
        TopTitle topTitle = (TopTitle) findViewById(R.id.create_position_tt);
        //noinspection deprecation
        topTitle.setTopParamter(getString(R.string.creat), getResources().getDrawable(R.drawable.back), null);
        onCanCreate();
    }

    @Override
    protected void onStationMode(Bundle savedInstanceState) {

    }

    @Override
    protected void onSuper() {

    }

    @Override
    protected void onRoot() {

    }

    @Override
    protected void onAdmin() {

    }

    @Override
    protected void onGuest() {

    }

    /**
     *拥有创建位置信息
     */
    private void onCanCreate() {
        creatBtn.setOnClickListener(new CreateLsn());

    }

    @Override
    public void onRequest() {

    }

    @Override
    public List<NameValuePair> getParamter() {
        return null;
    }

    @Override
    public void onSuccess(String json) {

    }

    @Override
    public void onReceive(Message message) {

    }

    private class CreateLsn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            onGallery();
//            onCamera();
//            onCameraSource();
            String url = "http://g.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600eca8b9aaeb50352ac65cb74d.jpg";
            HttpRequst.getHttpRequst().setInstance(CreatPositionAct.this);
            HttpRequst.getHttpRequst().getSourceFromNet(url, null, imageView);
        }

    }

    /**
     * 相册
     */
    private void onGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode ==100  ) {
            imageView.setImageURI(data.getData());
        }else if (requestCode == 101) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }else if (requestCode == 102) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        }
    }

    String filePath;

    /**
     * 拍摄原图
     */
     void onCameraSource() {
         filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + System.currentTimeMillis()+".png";
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         Uri uri = Uri.fromFile(new File(filePath));
         intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
         startActivityForResult(intent,102);
     }
    /**
     * 拍摄缩略图
     */
    private void onCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        startActivityForResult(intent, 101);
    }


}
