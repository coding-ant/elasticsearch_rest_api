package com.example.es.ioc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mario on 2018/7/16.
 */
public class User {
    private String name;
    public User(){
        System.out.println("user 初始化了。。。。。。。。。。。");
    }

    public User(String name){
        this.name = "wahaha";
        System.out.println("初始化了带name属性的");
    }

    public String sayHello(){
        Map map = new HashMap<>();
        Set<Map.Entry> entry = map.entrySet();
        return null;

    }
}
