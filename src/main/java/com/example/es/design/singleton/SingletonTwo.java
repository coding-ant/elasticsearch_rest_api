package com.example.es.design.singleton;

/**
 * Created by Mario on 2018/7/30.
 */
public class SingletonTwo {
    //懒汉模式
    private static SingletonTwo singletonTwo = null;

    private SingletonTwo(){}

    public static SingletonTwo getInstance(){
            if(singletonTwo == null) {
                singletonTwo = new SingletonTwo();
            }
            return singletonTwo;
    }
}
