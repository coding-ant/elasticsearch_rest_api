package com.example.es.design.adapter;

/**
 * Created by Mario on 2018/7/25.
 */
public class TV implements DoublePin {
    @Override
    public void electirify(int fire, int zero) {
        System.out.println("火线通电");
        System.out.println("零线通电");
    }
}
