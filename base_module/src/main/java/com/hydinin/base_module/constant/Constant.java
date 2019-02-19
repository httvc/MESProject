package com.hydinin.base_module.constant;

public class Constant {
    public static final String SERVERIP="192.168.1.120";//服务器ip
    public static final String SERVERPORT="8080";//服务器端口

    public static final String ISRECORD="isrecord";
    public  static class LoginInfo{
        public static final String LOGINNAME="loginname";//用户登录名
        public static final String LOGINPWD="loginpwd";//用户登录密码
        public static final String USERID="userId";//用户Id
        public static final String DEPARTID="departId";//部门Id
        public static final String COMPANYID="companyId";//公司Id
    }

    public static class IpInfo{
        public static final String IP="ip"; //服务器ip
        public static final String PORT="port";//服务器端口
    }

    public static final String VERSION_CODE="versioncode";//下载版本

    //页面间传值状态
    public static final int REQ_CODE = 10001;
    public static final int OPEN_ACTION_GET_CONTENT_IMAGE_COPY = 10002;
    public static final int REQUEST_IMAGE = 10003;
    public static final int REQUEST_CODE = 10004;
    public static final int RESULT_CODE = 10005;

    public static final int RESULT_INIT=10006;
    public static final int RESULT_TRUSS=10007;
    public static final int RESULT_HOOPING=10008;
}
