package com.hydinin.base_module.base;

import java.lang.ref.WeakReference;

public abstract class BasePresent<M,V> {

    public V mView;
    public M mModel;
    public WeakReference<V> mViewRef;

    public void attachModelView(M pModel,V pView){
        mViewRef = new WeakReference<V>(pView);
        this.mView = pView;
        this.mModel=pModel;
    }

    public V getmView(){
        if (isAttach()){
            return mViewRef.get();
        }else {
            return null;
        }
    }

    public boolean isAttach() {
        return null != mViewRef && null != mViewRef.get();
    }


    public void onDettach() {
        if (null != mViewRef) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
