package com.wenju.wenjupermissionutil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
        if (checkDangerousPermissions(activity, permissions)) {
            requestPermissions(activity, permissions, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        return this;
    }

    /**
     * 核对权限是否被允许
     *
     * @param permissions 权限数组
     */
    private Boolean checkDangerousPermissions(final @NonNull Activity activity, final @NonNull String[] permissions) {
        AlertDialog.Builder builder = null;
        //遍历权限数组，看是否有权限未被允许
        for (final String permission : permissions) {
            //第一次进入，点击拒绝不再提醒或者允许之后为false，其他为true
            boolean requestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission);
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                if (requestPermissionRationale) {
                    if (markedWords != null && builder == null) {
                        builder = new AlertDialog.Builder(activity);
                        builder.setMessage(markedWords)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        requestPermissions(activity, permissions, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        builder.show();
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("RestrictedApi")
    private static void requestPermissions(final @NonNull Activity activity, final @NonNull String[] permissions, final @IntRange(from = 0) int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity instanceof ActivityCompat.RequestPermissionsRequestCodeValidator) {
                ((ActivityCompat.RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(requestCode);
            }
            activity.requestPermissions(permissions, requestCode);
        } else if (activity instanceof ActivityCompat.OnRequestPermissionsResultCallback) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {
                    final int[] grantResults = new int[permissions.length];
                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();
                    final int permissionCount = permissions.length;
                    for (int i = 0; i < permissionCount; i++) {
                        grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
                    }
                    (activity).onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            });
        }
    }


}
