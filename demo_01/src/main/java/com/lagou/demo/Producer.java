package com.lagou.demo;

import java.util.concurrent.BlockingQueue;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/1
 */
public class Producer implements Runnable {

    private BlockingQueue<KouZhao> queue;

    private Integer index = 0;

    public Producer(BlockingQueue<KouZhao> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                if (queue.remainingCapacity() <= 0) {
                    System.out.println("口罩堆积如山了，大家快来买口罩...");
                } else {
                    KouZhao kouZhao = new KouZhao();
                    kouZhao.setId(index++);
                    kouZhao.setType("N95");
                    System.out.println("正在生产第" + (index - 1) + "号口罩...");
                    queue.put(kouZhao);
                    System.out.println("已经生产了" + queue.size() + "个口罩...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
