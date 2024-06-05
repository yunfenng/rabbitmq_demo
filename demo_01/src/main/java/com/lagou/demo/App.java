package com.lagou.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/1
 */
public class App {

    public static void main(String[] args) {
        BlockingQueue<KouZhao> queue = new ArrayBlockingQueue<KouZhao>(20);

        new Thread(new Producer(queue)).start();

        new Thread(new Consumer(queue)).start();
    }
}
