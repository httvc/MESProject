package com.hydinin.base_module.listener;

public interface MVPListener<E> {

    public void onSuccess(E content);

    public  void onFail(String msg);
}
