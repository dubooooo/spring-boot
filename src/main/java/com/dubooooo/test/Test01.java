package com.dubooooo.test;

import cn.hutool.core.util.URLUtil;

public class Test01 {

    public static void main(String[] args) {
        System.out.println(URLUtil.complateUrl("http://desk.zol.com.cn/asd/", "/bizhi/7278_90029_2.html"));
        System.out.println(URLUtil.complateUrl("http://desk.zol.com.cn/pc/hot_1.html", "/bizhi/7278_90029_2.html"));
    }

}
