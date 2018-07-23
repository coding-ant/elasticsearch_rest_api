package com.example.es.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Mario on 2018/7/16.
 */
@Controller
public class TestController {


    @RequestMapping("/test")
    public void test(){

    }
}
