package com.wenju.wjpermissionutil;

import android.Manifest;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wenju.wenjupermissionutil.WenJuPermissionUtil;

public class PermissionToRequestDemo extends AppCompatActivity {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_permission_to_request_demo);
        final String[] permissions = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE};
        new WenJuPermissionUtil(this, permissions)
                .setDialogTitle("建议开启所有权限,否者某些功能将无法使用")
                .requestPermisssion();

        findViewById(R.id.request_permission_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WenJuPermissionUtil(activity, new String[]{Manifest.permission.CALL_PHONE})
                        .setDialogTitle("建议电话权限,否者某些功能将无法使用")
                        .requestPermisssion();
            }
        });
    }

}

