package com.example.es.design.singleton;

import org.elasticsearch.common.inject.Singleton;

/**
 * Created by Mario on 2018/7/30.
 */
public class SingletonOne {
    //饿汉模式
    private final static SingletonOne singletonOne = new SingletonOne();

    private SingletonOne(){};
    public static SingletonOne getInstance(){
        return singletonOne;
    }
}
