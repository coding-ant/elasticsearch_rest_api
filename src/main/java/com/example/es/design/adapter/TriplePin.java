package com.example.es.design.adapter;

/**
 * Created by Mario on 2018/7/25.
 */
public interface TriplePin {
    //参数分别为火线 fire 零线 zero和  底线floor

    public void electrify(int fire,int zero,int floor);
}
