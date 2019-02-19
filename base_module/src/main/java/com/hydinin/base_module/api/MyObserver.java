package com.hydinin.base_module.api;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.util.MalformedJsonException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.hydinin.base_module.BaseApplication;
import com.hydinin.base_module.utils.UnicodeUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Android Studio
 * Project：ZhiChuang
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2018/3/20.
 */

public abstract class MyObserver<T extends HttpResult> implements Observer<T> {
    private final String TAG = MyObserver.class.getSimpleName();
    public final static String Thread_Main="main";
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private static Gson gson = new Gson();

    private String errorMsg = "未知的错误！";

    private Disposable disposable;

    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法！Error 是选择重写，but 必须Super ！
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    public abstract void onFailure(boolean success, String message);
    /**
     * @param mCtx
     */
    public MyObserver(Context mCtx) {
        this.mContext = mCtx;
    }

    /**
     * @param mCtx
     * @param msg
     */
    public MyObserver(Context mCtx, String msg) {
        this.mContext = mCtx;
        showDialog(msg, mContext);
    }
    @Override
    public void onSubscribe(Disposable d) {
        disposable=d;
    }

    @Override
    public void onNext(T response) {
        dismissDialog();
        if(!disposable.isDisposed()){
            disposable.dispose();
        }
        if (response.isSuccess()){
            onSuccess(response);
        }/*else if (response.getCode()==400){
            if ("登陆超时，请重新登陆".equals(response.getMsg())){
                SPUtil.clear(mContext);
              //  Intent intent=new Intent(mContext, LoginActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               // mContext.startActivity(intent);
            }
            onFailure(false,response.getMsg());
        }*/else {
            onFailure(false,response.getMsg());
        }
    }

    /**
     * 通用异常错误的处理，不能弹出一样的东西出来
     *
     *
     * @param t
     */
    @Override
    public final void onError(Throwable t) {
        dismissDialog();
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorMsg = "网络错误";
            getErrorMsg(httpException);
        }else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException
                || t instanceof MalformedJsonException) {  //解析数据错误
            errorMsg ="解析错误";
        } else if (t instanceof ConnectException) {//连接网络错误
            errorMsg ="网络连接异常，请检查您的网络状态，稍后重试！";
        }else if (t instanceof SocketTimeoutException) {  //VPN open
            errorMsg = "网络连接超时，请检查您的网络状态，稍后重试！";
        }else if (t instanceof UnknownHostException) {
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            errorMsg = "没有网络，请检查网络连接";
        } else if (t instanceof NetworkOnMainThreadException) {//主线程不能网络请求，这个很容易发现
            errorMsg = "主线程不能网络请求";
        } else if (t instanceof RuntimeException) { //很多的错误都是extends RuntimeException
            errorMsg = "运行时错误";
        }else {
            errorMsg ="未知错误";
        }
        onFailure(false, errorMsg);
    }

    @Override
    public void onComplete() {

    }




    /**
     * 获取详细的错误的信息 errorCode,errorMsg
     * <p>
     * 以登录的时候的Grant_type 故意写错为例子,这个时候的http 应该是直接的返回401=httpException.code()
     * 但是是怎么导致401的？我们的服务器会在respose.errorBody 中的content 中说明
     */
    private final void getErrorMsg(HttpException httpException) {
        String errorBodyStr = "";
        try {      //我们的项目需要的UniCode转码 ,!!!!!!!!!!!!!!
            errorBodyStr = UnicodeUtils.convertUnicode(httpException.response().errorBody().string());
        } catch (IOException ioe) {
            Log.e("errorBodyStr ioe:", ioe.toString());
        }
        try {
            HttpResult errorResponse = gson.fromJson(errorBodyStr, HttpResult.class);
            if (null != errorResponse) {
                onFailure(false, errorResponse.getMsg());
                errorMsg = errorResponse.getMsg();
            }else {
                onFailure(false, "ErrorResponse is null ");  //!!!!!!
            }
        } catch (Exception jsonException) {
            onFailure(false, "http请求错误Json 信息异常"); //
            jsonException.printStackTrace();
        }
    }

    public void showDialog(String message,Context mContext) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
