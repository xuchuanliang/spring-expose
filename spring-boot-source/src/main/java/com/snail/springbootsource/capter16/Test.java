package com.snail.springbootsource.capter16;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class Test {

    public static void test1() {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod("www.baidu.com");

    }
}
