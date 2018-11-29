package com.wenju.wjpermissionutil;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wenju.wenjupermissionutil.WenJuPermissionUtil;

public class PermissionToRequestDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_to_request_demo);
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS};
        new WenJuPermissionUtil(this, permissions)
                .setDialogTitle("建议开启所有权限,否者某些功能将无法使用")
                .requestPermisssion();
    }
}
