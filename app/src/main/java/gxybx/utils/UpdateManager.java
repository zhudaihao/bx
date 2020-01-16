package gxybx.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import cn.gxybx.R;
import gxybx.ui.activity.MainActivity;

import static com.zhy.base.fileprovider.FileProvider7.getUriForFile24;


/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /*
     * 检测更新
     */
    private static final int UPDATE = 3;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 版本号 */
    private long servicecode;
    private long localcode;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    private String versionCode = "";
    private Activity mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private boolean isupdate = true;
    private HashMap<String, String> map;
    private String downloadpath, serviceCode, updateInfo;
    private boolean flag;

    private boolean isOneLogin;//刚打开APP 是否强制更新

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case UPDATE:
                    if (servicecode > localcode) {
                        isupdate = false;
                    }
                    if (!isupdate) {
                        // 显示提示对话框
                        // showNoticeDialog();
                    } else {
                        Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_LONG).show();
                    }
                    break;
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpdateManager(Activity context, boolean flag, boolean isOneLogin) {
        this.mContext = context;
        this.flag = flag;
        this.isOneLogin = isOneLogin;
    }


    /**
     * 获取软件版本号
     */
    public int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.szy.update", 0).versionCode;
        } catch (NameNotFoundException e) {

        }
        return versionCode;
    }

    /**
     * 关闭对话框
     */
    public void dismmDialog() {
        if (noticeDialog != null && noticeDialog.isShowing()) {
            noticeDialog.dismiss();
        }

    }


    /**
     * 显示软件更新对话框
     */
    private Dialog noticeDialog;

    //isUpdate	number	是否必须更新（0:否；1:是）
    public void showNoticeDialog(final String downloadPath) {
        // 构造对话框
        Builder builder = new Builder(mContext);
        builder.setTitle("软件更新");
        builder.setMessage("检测到新版本，是否立即更新？");
        builder.setCancelable(false);
        // 更新
        builder.setPositiveButton("立即更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                // 显示下载对话框
                //	 启动新线程下载软件
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0);
                } else {
                    showDownloadDialog(downloadPath);
                }
            }
        });

        if (!isOneLogin) {
            //取消更新
            builder.setNegativeButton("稍后更新", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    /**
                     * 跳转到mainactivity
                     */
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(gxy.library.R.anim.anim_left, 0);
                    mContext.finish();

                }
            });
        }


        noticeDialog = builder.create();
        noticeDialog.show();
    }


    /**
     * 显示软件下载对话框
     */
    public void showDownloadDialog(String downloadPath) {

        downloadpath = downloadPath;
        // 构造软件下载对话框
        Builder builder = new Builder(mContext);
        builder.setTitle("正在更新");
        builder.setMessage(updateInfo);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
//        builder.setNegativeButton("", null);
//        builder.setNegativeButton("取消", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//                // 设置取消状态
//                cancelUpdate = true;
//
//
//            }
//        });
        mDownloadDialog = builder.create();
        mDownloadDialog.setCanceledOnTouchOutside(false);
        mDownloadDialog.show();
        // 下载文件
        downloadApk();


    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(downloadpath);
                    URLConnection conn = null;
                    // HttpURLConnection conn1 ;
                    if (url.getProtocol().equals("http")) {
                        conn = (HttpURLConnection) url.openConnection();
                    } else if (url.getProtocol().equals("https")) {
                        // 创建连接
                        conn = (HttpsURLConnection) url.openConnection();
                    }
                    if (conn != null) {
                        conn.connect();
                    }
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, "bx_gxy");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {

            } catch (IOException e) {

            }
            // 取消下载对话框显示
//			mDownloadDialog.dismiss();
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private Uri fileUri;

    private void installApk() {
        File file = new File(mSavePath, "bx_gxy");
        Intent intent = new Intent(Intent.ACTION_VIEW);

        //7.0
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(getUriForFile(mContext, file), "application/vnd.android.package-archive");
            mContext.startActivity(intent);

        } else {
            fileUri = Uri.fromFile(file);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            mContext.startActivity(intent);
        }

        //关闭activity
        mContext.finish();


    }

    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }


}
