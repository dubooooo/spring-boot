package com.dubooooo.common;

import com.dubooooo.entity.SourceEntity;

import java.util.concurrent.BlockingDeque;

public class Sourcedownload implements Runnable {

    private BlockingDeque<SourceEntity> blockingDeque;

    public Sourcedownload(BlockingDeque<SourceEntity> blockingDeque) {
        this.blockingDeque = blockingDeque;
    }

    @Override
    public void run() {
        try {
            SourceEntity sourceEntity = blockingDeque.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
