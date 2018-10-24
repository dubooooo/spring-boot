package com.dubooooo.spider.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.dubooooo.entity.ImageEntity;
import com.dubooooo.entity.SpiderMainEntity;
import com.dubooooo.repository.ImageEntityRepository;
import com.dubooooo.repository.SpiderMainRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

//@Component
public class HtmlImgProcess implements Runnable {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    ImageEntityRepository imageEntityRepository;
    @Autowired
    SpiderMainRepository spiderMainRepository;
    boolean start;

    @PostConstruct
    public void autorun() {
        start = true;
        taskExecutor.execute(this);
    }

    @PreDestroy
    public void stop() {
        start = false;
    }

    @Override
    public void run() {
        while (start) {
            try {
                SpiderMainEntity spiderMainEntity = spiderMainRepository.findFirstByStatus("1");
                if (spiderMainEntity == null) {
                    System.out.println("所有网页已处理完毕...");
                    Thread.sleep(3000);
                    continue;
                }
                Document document = Jsoup.parse(spiderMainEntity.getHtml());
                Elements elements = document.body().getElementsByTag("img");
                for (Element element : elements) {
                    try {
                        System.out.println(element);
                        String href = element.attr("src");
                        if (StrUtil.isEmpty(href)) {
                            href = element.attr("srcs");
                        }
                        if (StrUtil.isEmpty(href)) {
                            href = element.attr(".src");
                        }
                        if (StrUtil.isEmpty(href)) {
                            href = element.attr("loadsrc");
                        }
                        if (StrUtil.isEmpty(href)) {
                            href = element.attr("srch");
                        }
                        if (StrUtil.isEmpty(href)) {
                            href = element.attr("srcs");
                        }
                        if (StrUtil.isEmpty(href)) {
                            System.out.println("图片链接为空-> " + href);
                        } else {
                            href = URLUtil.complateUrl(spiderMainEntity.getUrl(), href);
                            if (imageEntityRepository.countByUrl(href) == 0) {
                                System.out.println("图片链接保存成功-> " + href);
                                ImageEntity img = new ImageEntity();
                                img.setHost(spiderMainEntity.getHost());
                                img.setUrl(href);
                                img.setAlt(element.attr("alt"));
                                img.setTitle(element.attr("title"));
                                img.setImg(element.toString());
                                img.setStatus("0");
                                img.setInsertDate(new Date());
                                imageEntityRepository.save(img);
                            } else {
                                System.out.println("图片链接重复-> " + href);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("图片链接处理异常" + e);
                    }
                }
                spiderMainEntity.setStatus("3");
                spiderMainRepository.save(spiderMainEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
