package net;

import android.app.Activity;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import baseBean.BaseResponseEntity;
import utils.LogUtils;
import view.DialogUtils;


/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author YOLANDA;
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    /**
     * Dialog.
     */

    private Request<?> mRequest;

    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    /**
     * 是否显示dialog.
     */
    private boolean isLoading;
    private DialogUtils dialogUtils;
    private Activity context;

    /**
     * @param context      context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(Activity context, Request<?> request, HttpListener<T> httpCallback,
                                boolean canCancel, boolean isLoading) {
        this.context = context;
        this.mRequest = request;

        if (isLoading) {
            dialogUtils = new DialogUtils();
        }

        this.callback = httpCallback;
        this.isLoading = isLoading;
    }

    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
     /* if (dialogUtils!=null){
          dialogUtils.shownDialogProgress(context);
      }*/

    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        dismissDialog();
    }


    /**
     * 返回成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        BaseResponseEntity back = null;
        try {
            back = JSON.parseObject(response.get().toString(), BaseResponseEntity.class);
            /**
             * 是否显示后台信息
             */
            if ("true".equals(back.getMsg().getIsVisible())) {
                Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                toast.setText(back.getMsg().getInfo());
                toast.show();
            }


            //判断登录是否过期
//            CheckUtil cu = new CheckUtil(new CheckLoginInfo(context, back.getMsg().getType()));
//            if(!MyApp.ISSHOWDIALOG) {
//              new CheckLoginInfo(context, back.getMsg().getType()).check();
//            }


            /**
             * 0、2成功回调。其他失败回调
             */
            switch (back.getMsg().getType()) {
                case 0:
                case 2:
                    if (callback != null) {
                        LogUtils.json(back.getData().toString());
                        callback.onSuccessful(back.getCommon().getRequestWhat(), back.getData(), back.getPages());
                        dismissDialog();
                    }
                    break;
                default:
                    if (callback != null) {
                        //返回失败回调
                        callback.onFailure(back.getCommon().getRequestWhat(), back.getData());

                        dismissDialog();
                    }
                    break;


            }


        } catch (Exception e) {
            /**
             * 防止后台格式错误
             */
            dismissDialog();


        }
    }

    protected void dismissDialog() {
        if (dialogUtils != null) {
            dialogUtils.setDismiss();
        }
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        //关闭dialog
        dismissDialog();

        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            Toast.makeText(context, "网络不好", Toast.LENGTH_SHORT).show();
        } else if (exception instanceof TimeoutError) {// 请求超时
            Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            Toast.makeText(context, "找不到服务器", Toast.LENGTH_SHORT).show();
        } else if (exception instanceof URLError) {// URL是错的
            Toast.makeText(context, "URL是错的", Toast.LENGTH_SHORT).show();
        }
        // Logger.e("错误:" + response.getException().getMessage());

        if (callback != null)
            callback.onFailed(what, response);
    }


}
