package com.example.es.design.strategy;

/**
 * Created by Mario on 2018/7/30.
 */
public class MultiplyCalculator implements  Calculator {
    @Override
    public int calculator(int x, int y) {
        return x*y;
    }
}
