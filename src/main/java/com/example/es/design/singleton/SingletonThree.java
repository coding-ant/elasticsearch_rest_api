package com.example.es.design.singleton;

/**
 * Created by Mario on 2018/7/30.
 */
public class SingletonThree {
    //双重检查
    private static volatile SingletonThree singletonThree;

    private SingletonThree(){};

    public static SingletonThree getInstance(){
        if(singletonThree == null){
            synchronized (SingletonThree.class){
                if(singletonThree == null){
                    singletonThree = new SingletonThree();
                }
            }
        }
            return singletonThree;
    }
}
