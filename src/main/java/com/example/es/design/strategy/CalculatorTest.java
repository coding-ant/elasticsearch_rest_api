package com.example.es.design.strategy;

/**
 * Created by Mario on 2018/7/30.
 */
public class CalculatorTest {

    public static void main(String[] args){
        Calculator cal = new AddCalculator();
        int result = cal.calculator(2,3);
        System.out.println(result);
    }
}
