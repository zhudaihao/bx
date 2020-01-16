package net;

public class NetRequest {
    //测试环境
//     public static String SERVER = BaseApp.getInstance().getService();


    //线上环境  http://cms-app.gxy-bx.com/
    public static String SERVER = "http://cms-app.gxy-bx.com";

    public static final String UPDATE = SERVER + "/home/v1/getLatestVersion";//升级

    public static final String HOME = SERVER + "/home/v1/index";//首页
    public static final String INDUSTRY = SERVER + "/home/v1/articles";//•行业资讯列表
    public static final String INFO=SERVER+"/home/v1/getAboutUsUrl";//公司简介
    public static final String ME = SERVER + "/home/v1/contactUs";//联系我们

    public static final String NEW = SERVER + "/home/v1/getArticleDetailUrl";//行业资讯详情


}