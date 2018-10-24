package com.dubooooo.spider.common;

import com.dubooooo.entity.SpiderMainEntity;
import com.dubooooo.repository.SpiderMainRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.List;

/**
 * 爬取的网页添加title
 */
//@Component
public class SpiderHtmlTitleProcess implements Runnable {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
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

    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.piccnet.com.cn", 3128));

    @Override
    public void run() {
        while (start) {
            List<SpiderMainEntity> spiderMainEntityList = null;
            try {
                spiderMainEntityList = spiderMainRepository.findByTitleIsNullAndHtmlIsNotNull(PageRequest.of(1, 10));
                if (spiderMainEntityList.size() == 0) {
                    Thread.sleep(3000);
                    continue;
                }
                for (SpiderMainEntity spiderMainEntity : spiderMainEntityList) {
                    Document document = Jsoup.parse(spiderMainEntity.getHtml());
                    spiderMainEntity.setTitle(document.title());
                    spiderMainEntity.setModifyDate(new Date());
                    spiderMainRepository.save(spiderMainEntity);
                    System.out.println("Title处理成功-> " + spiderMainEntity.getTitle());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
