package com.example.es.design.adapter;

/**
 * Created by Mario on 2018/7/25.
 */
public class PinAdapter implements TriplePin {

    private DoublePin doublePin;

    //创建适配器的时候需要把双插设备接进来
   public PinAdapter(DoublePin doublePin){
        this.doublePin  = doublePin;
    }

    //适配器实现的是目标接口
    @Override
    public void electrify(int fire, int zero, int floor) {
       //实际上调用了被适配的双插通电，地线丢弃了
        doublePin.electirify(fire,zero);
    }
}
