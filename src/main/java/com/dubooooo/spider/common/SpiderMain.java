package com.dubooooo.spider.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.dubooooo.entity.SpiderMainEntity;
import com.dubooooo.repository.SpiderMainRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//@Component
public class SpiderMain {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    SpiderMainRepository spiderMainRepository;
    boolean start;
    private static final Object lock = new Object();
    private static final BlockingQueue<SpiderMainEntity> BLOCKING_QUEUE = new ArrayBlockingQueue<>(100);
    private static final List<SpiderMainEntity> SPIDER_MAIN_ENTITY_LIST = new ArrayList<>();
    private static final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.piccnet.com.cn", 3128));

    @PostConstruct
    public void autorun() {
        System.out.println("网页抓取线程启动成功...");
        start = true;
        taskExecutor.execute(start_process());
        taskExecutor.execute(start_process());
        taskExecutor.execute(start_process());
    }

    @PreDestroy
    public void stop() {
        start = false;
    }

    public SpiderMainEntity getEntity() {
        synchronized (lock) {
            Page<SpiderMainEntity> spiderMainEntity = spiderMainRepository.findAll(PageRequest.of(0, 100));
            spiderMainEntity.forEach(e -> {
                try {
                    SPIDER_MAIN_ENTITY_LIST.add(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            return SPIDER_MAIN_ENTITY_LIST.get(0);
        }
    }

    public Runnable start_process() {
        Runnable runnable = () -> {
            while (start) {
                SpiderMainEntity spiderMainEntity = null;
                try {
                    spiderMainEntity = BLOCKING_QUEUE.take();
                    if (spiderMainEntity == null) {
                        Thread.sleep(3000);
                        continue;
                    }
                    System.out.println("请求链接地址-> " + spiderMainEntity.getUrl());
                    Document document = Jsoup.connect(spiderMainEntity.getUrl()).proxy(proxy).get();
                    spiderMainEntity.setHtml(document.html());
                    spiderMainEntity.setTitle(document.title());
                    Elements elements = document.body().getElementsByTag("a");
                    for (Element element : elements) {
                        try {
                            System.out.println(element);
                            String href = element.attr("href");
                            href = URLUtil.complateUrl(spiderMainEntity.getUrl(), href);
                            if (StrUtil.isNotEmpty(href) && StrUtil.startWith(href, spiderMainEntity.getHost()) && spiderMainRepository.countByUrl(href) == 0) {
                                SpiderMainEntity grabbing = new SpiderMainEntity();
                                grabbing.setInsertDate(new Date());
                                grabbing.setStatus("0");
                                grabbing.setHost(spiderMainEntity.getHost());
                                grabbing.setUrl(href);
                                spiderMainRepository.save(grabbing);
                                System.out.println("链接保存成功-> " + href);
                            } else {
                                System.out.println("链接过滤-> " + href);
                            }
                        } catch (Exception e) {
                            System.out.println("链接处理异常-> " + e);
                        }
                    }
                    spiderMainEntity.setStatus("1");

                } catch (Exception e) {
                    e.printStackTrace();
                    if (spiderMainEntity != null) {
                        spiderMainEntity.setStatus("2");
                    }
                } finally {
                    if (spiderMainEntity != null) {
                        spiderMainEntity.setModifyDate(new Date());
                        spiderMainRepository.save(spiderMainEntity);
                    }
                }
            }
        };
        return runnable;
    }

}
