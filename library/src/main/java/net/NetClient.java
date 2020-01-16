package net;


import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.rest.JsonObjectRequest;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.StringRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import baseBean.RequestEntity;
import baseBean.RequestPublic;
import encoder.AESCrypt;
import utils.SSLContextUtil;
import utils.Util;


/**
 * 访问网络的控制
 *
 * @author Ender
 */

public class NetClient {
    public HttpResponseListener responseListener;
    public static int OSTYPE = 2;

    /**
     * 请求队列.
     */
    private RequestQueue requestQueue;
    private StringRequest request;
    private StringRequest uploadrequest;
    private JsonObjectRequest jsobjRequest;

    private AESCrypt aesc;
    private Activity context;
    protected HashMap<String, Object> params = null;
    private HttpListener listener;
    private DownloadQueue downloadQueue;
    private DownloadRequest downloadRequest;

    public NetClient(RequestQueue requestQueue, Activity context, HttpListener<String> listener) {
        super();
        try {
            aesc = new AESCrypt();
        } catch (Exception e) {
        }
        params = new HashMap<>();
        this.listener = listener;
        this.context = context;
        this.requestQueue = requestQueue;

    }

    //初始化公共参数
    public static int LANGUAGE = 1;

    private void initPublicParams() {
        params.put("osType", OSTYPE);
        params.put("version", Util.getAppVersion(context));
        params.put("imei", Util.getIMEI(context));
        params.put("language", LANGUAGE);
    }


    //初始化公共参数
    private void initPublicParamsAdd() {
        params.put("osType", OSTYPE);
        params.put("version", "1.3");
        params.put("imei", Util.getIMEI(context));
        params.put("language", LANGUAGE);
    }

    /**
     * _______________________________________架构部分___________________________________________
     */
    protected void get(String url, String requestWhat, boolean isshowloading) {
        //创建get请求对象
        initPublicParams();
        request = (StringRequest) NoHttp.createStringRequest(url, RequestMethod.GET);

        //同意添加请求标识
        params.put("requestWhat", requestWhat);
        //添加请求参数
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val instanceof String) {
                request.add(key, (String) val);
            } else if (val instanceof Integer) {
                request.add(key, (int) val);
            }

        }

        try {
            //添加签名
            request.add("signture", Util.getSignature(Util.mapToString(params), "4DFF2D1F474B3215FEA02799774DB76A"));
        } catch (IOException e) {
        }

        responseListener = new HttpResponseListener(context, request, listener, true, isshowloading);

        // responseListener = new HttpResponseListener< >(context, request, listener, true, isshowloading);
        requestQueue.add(0, request, responseListener);


    }


    protected void get(String url, String requestWhat) {
        //创建get请求对象
        initPublicParams();
        request = (StringRequest) NoHttp.createStringRequest(url, RequestMethod.GET);

        //同意添加请求标识
        params.put("requestWhat", requestWhat);
        // 添加请求参数
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val instanceof String) {
                request.add(key, (String) val);
            } else if (val instanceof Integer) {
                request.add(key, (int) val);
            }

        }

        try {
            //添加签名
            request.add("signture", Util.getSignature(Util.mapToString(params), "4DFF2D1F474B3215FEA02799774DB76A"));
        } catch (IOException e) {
        }


        responseListener = new HttpResponseListener<>(context, request, listener, true, true);
        requestQueue.add(0, request, responseListener);


    }

    protected void getNew(String url, String requestWhat) {
        //创建get请求对象
        initPublicParamsAdd();
        request = (StringRequest) NoHttp.createStringRequest(url, RequestMethod.GET);

        //同意添加请求标识
        params.put("requestWhat", requestWhat);
        // 添加请求参数
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val instanceof String) {
                request.add(key, (String) val);
            } else if (val instanceof Integer) {
                request.add(key, (int) val);
            }

        }

        try {
            //添加签名
            request.add("signture", Util.getSignature(Util.mapToString(params), "4DFF2D1F474B3215FEA02799774DB76A"));
        } catch (IOException e) {
        }


        responseListener = new HttpResponseListener<>(context, request, listener, true, true);
        requestQueue.add(0, request, responseListener);
    }


    protected void post(HashMap<String, Object> param, String requestWhat, String url, boolean issecurity, boolean isshowloading) {
        //初始化公共参数
        jsobjRequest = (JsonObjectRequest) NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
        RequestEntity body = new RequestEntity();
        RequestPublic common = new RequestPublic();
        common.getClient().setImei(Util.getIMEI(context));
        common.getClient().setOsType(OSTYPE);
        common.getClient().setVersion(Util.getAppVersion(context) + "");
        common.setLanguage(LANGUAGE + "");
        body.setCommon(common);
        body.setRequestWhat(requestWhat);
        //签名
        try {
            String sigh = Util.getSignature(Util.mapToString(param), "4DFF2D1F474B3215FEA02799774DB76A");
            param.put("signture", sigh);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        //对data加密
        String data = "";
        String key = "";
        if (issecurity) {
            try {
                //随机密钥
                key = DesUtil.getKeyString();
                //DES加密data
                //  LogUtils.e("加密前key>>data" + key + ">>>>" + JSON.toJSONString(param, true));
                data = DesUtil.encrypt(JSON.toJSONString(param, true), key);
                body.setData(data);
                //AES加密DES的密钥
                body.setSecurity(aesc.encrypt(key));
            } catch (Exception e) {
                //e.printStackTrace();
            }
        } else {
            data = JSON.toJSONString(param, true);
            body.setData(data);
        }

        jsobjRequest.setDefineRequestBodyForJson(JSON.toJSONString(body));
        //加密加密私钥上传服务器
        responseListener = new HttpResponseListener<>(context, jsobjRequest, listener, true, isshowloading);
        requestQueue.add(0, jsobjRequest, responseListener);
    }


    protected void upLoad(String token, FileBinary file, String requestWhat, String url, boolean isshowloading) {
        //初始化公共参数
        uploadrequest = (StringRequest) NoHttp.createStringRequest(url, RequestMethod.POST);
        uploadrequest.add("source", 2 + "");
        uploadrequest.add("token", token);
        uploadrequest.add("headImg", file);
        uploadrequest.add("requestWhat", requestWhat);
        //加密加密私钥上传服务器
        responseListener = new HttpResponseListener<>(context, uploadrequest, listener, true, isshowloading);
        requestQueue.add(0, uploadrequest, responseListener);
    }

    protected void download(String url, DownloadListener downloadListener, boolean isshowloading) {
        downloadQueue = NoHttp.newDownloadQueue(1);
        //初始化公共参数
        downloadRequest = NoHttp.createDownloadRequest(url, Util.getSDPath(), "gxyclub.apk", true, isshowloading);
        //加密加密私钥上传服务器

        SSLContext sslContext = SSLContextUtil.getSSLContext();
        if (sslContext != null)
            downloadRequest.setSSLSocketFactory(sslContext.getSocketFactory());
        responseListener = new HttpResponseListener<>(context, uploadrequest, listener, true, isshowloading);
        downloadQueue.add(0, downloadRequest, downloadListener);
    }


    /**
     *  ----------------------------------------------- 请求部分      --------------------------------------------------
     */


    /**
     * 版本检测
     */

    public void checkUpdate() {
        get(NetRequest.UPDATE, RequestWhat.UPDATE, false);

    }

    /**
     * 首页
     */
    public void home() {
        get(NetRequest.HOME, RequestWhat.HOME);
    }


    /**
     * •行业资讯列表
     */
    public void getIndustry(int pageNum) {
        params.put("pageNum", pageNum);
        params.put("pageSize", 10);
        get(NetRequest.INDUSTRY, RequestWhat.INDUSTRY);

    }

     /**
     * me数据
     */
    public void getMe() {
        get(NetRequest.ME, RequestWhat.ME);
    }

    /**
     * 行业资讯详情
     */
    public void getNew(int id) {
        params.put("id",id);
        get(NetRequest.NEW, RequestWhat.NEW);
    }


    /**
     * 公司简介
     */
    public void getCompanyInfo() {
        get(NetRequest.INFO, RequestWhat.INFO);
    }


}
