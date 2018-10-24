package com.dubooooo.spider.zol;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ZolMain {

    public static void main(String[] args) {
        new ZolMain().start();
    }

    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.piccnet.com.cn", 3128));
    String url = "http://desk.zol.com.cn";

    private void start() {
        List<String> url_list = new ArrayList();
        url_list.add(url);
        try {
            Document document = Jsoup.connect(url).proxy(proxy).get();
            Elements elements = document.getElementsByTag("img");
            for (Element element : elements) {
                System.out.println(element);
                String img_url = element.attr("src");
                if (StrUtil.isEmpty(img_url)) {
                    img_url = element.attr("srch");
                }
                if (StrUtil.isEmpty(img_url)) {
                    img_url = element.attr("loadsrc");
                }
                System.out.println(img_url);
                if (img_url != null && img_url.startsWith("https://")) {
                    byte[] img_bytes = Jsoup.connect(img_url).proxy(proxy).ignoreContentType(true).execute().bodyAsBytes();
                    File img_file = new File("e:/img/" + getFileName(img_url));
                    if (!img_file.exists()) {
                        File file = FileUtil.writeBytes(img_bytes, img_file);
                        System.out.println(file);
                    }
                }
            }
            elements = document.body().getElementsByTag("a");
            for (Element element : elements) {
                System.out.println(element);
                System.out.println(element.attr("href"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileName(String url) {
        if (url != null) {
            url = url.substring(url.lastIndexOf("/"));
            //url = url.substring(url.lastIndexOf("/"), url.indexOf("?") == 0 ? url.length() : url.indexOf("?"));
        }
        return url;
    }

}
