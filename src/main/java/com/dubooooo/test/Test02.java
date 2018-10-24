package com.dubooooo.test;

public class Test02 {

    private int x, y;
    private String[][] map;

    public static void main(String[] args) {
//        new Test02().start();
        new Thread(() -> {
            while (true) {
                try {
                    System.out.print("\r" + System.currentTimeMillis());
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            System.out.println();
            while (true) {
                try {
                    System.out.print("\r" + System.currentTimeMillis());
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void start() {
        init();
        play();
    }

    public void init() {
        map = new String[x][y];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public void play() {
        while (true) {
            draw();
            update();
        }
    }

    public void draw() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public void update() {
        draw();
        update();
    }

}
