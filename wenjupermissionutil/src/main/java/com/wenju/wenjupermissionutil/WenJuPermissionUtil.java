package com.wenju.wenjupermissionutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @author wenju
 * Date 2018/11/28，Time 10:12
 */
public class WenJuPermissionUtil {
    public final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private Activity activity;
    private String[] permissions;
    private String markedWords;

    public WenJuPermissionUtil(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
    }

    public WenJuPermissionUtil setDialogTitle(String markedWords) {
        this.markedWords = markedWords;
        return this;
    }

    public WenJuPermissionUtil requestPermisssion() {
        checkDangerousPermissions(activity, permissions);
        return this;
    }

    /**
     * 核对权限是否被允许
     *
     * @param permissions 权限数组
     */
    private void checkDangerousPermissions(final @NonNull Activity activity, final @NonNull String[] permissions) {
        AlertDialog.Builder builder = null;
        //首次进入权限申请
        SharedPreferences mFirstRequest = activity.getSharedPreferences("mFirstRequest",Context.MODE_PRIVATE);
        if(0 == mFirstRequest.getInt("mFirstRequest",0)){
            ActivityCompat.requestPermissions(activity, permissions, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            SharedPreferences.Editor edit = mFirstRequest.edit();
            edit.putInt("mFirstRequest",1);
            edit.apply();
        }else {
            //遍历权限数组
            for (final String permission : permissions) {
                //checkSelfPermission方法查看权限是否被允许，-1不允许，0允许
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                   //首次请求，被允许或者拒绝并且不再提醒的权限shouldShowRequestPermissionRationale方法返回false，其他为true
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            permission)) {
                        if (markedWords != null && builder == null) {
                            builder = new AlertDialog.Builder(activity);
                            builder.setMessage(markedWords)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ActivityCompat.requestPermissions(activity, permissions, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                        }
                                    });
                            builder.show();
                        }
                    }
                }
            }
        }
    }
}
