package com.example.xing.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2020-06-01 16:45.
 */
public class BlockQueueTest {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 阻塞队列
         */
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(3);



//        testException(blockingQueue);

//        testBool(blockingQueue);


//        testBlock(blockingQueue);


        testBlockTimeOut(blockingQueue);

        /**
         *  队列只有一个元素，如果想插入多个，必须等队列元素取出后，才能插入，只能有一个“坑位”，用一个插一个
         * 测试 SynchronousQueue
         */

        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                synchronousQueue.put("1");
                System.out.println("put 1");
                Thread.sleep(3000);
                System.out.println("put 2");
                synchronousQueue.put("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                System.out.println("take "+synchronousQueue.take());
                System.out.println("take "+synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    /**
     * 测试阻塞超时
     * @param blockingQueue
     * @throws InterruptedException
     */
    private static void testBlockTimeOut(BlockingQueue<Integer> blockingQueue) throws InterruptedException {
        blockingQueue.offer(1,2L, TimeUnit.SECONDS);
        blockingQueue.offer(1,2L, TimeUnit.SECONDS);
        blockingQueue.offer(1,2L, TimeUnit.SECONDS);
        blockingQueue.offer(1,2L, TimeUnit.SECONDS);  //超过指定时间还是阻塞，就会跳过阻塞

        System.out.println("---- ");

        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L, TimeUnit.SECONDS));  //超过指定时间还是阻塞，就会跳过阻塞
        System.out.println("---- ");
    }


    /**
     * 测试阻塞
     * @param blockingQueue
     * @throws InterruptedException
     */
    private static void testBlock(BlockingQueue<Integer> blockingQueue) throws InterruptedException {
        blockingQueue.put(1);
        blockingQueue.put(1);
        blockingQueue.put(1);
//        blockingQueue.put(1);  // 队列满了 一直阻塞
        System.out.println("put  结束");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
//        blockingQueue.take(); //队列没有元素 一直阻塞

        System.out.println(" take 结束");
    }

    /**
     * 返回bool
     * @param blockingQueue
     */
    private static void testBool(BlockingQueue<Integer> blockingQueue) {
        System.out.println(blockingQueue.offer(1));
        System.out.println(blockingQueue.offer(1));
        System.out.println(blockingQueue.offer(1));
//        System.out.println(blockingQueue.offer(1)); //返回 false
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());  //返回 null
    }

    /**
     *  异常测试组
     * @param blockingQueue
     */
    private static void testException(BlockingQueue<Integer> blockingQueue) {
        blockingQueue.add(1);
        blockingQueue.add(2);
        blockingQueue.add(3);
//        blockingQueue.add(1);  //Queue full  IllegalStateException
        System.out.println("------");
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
//        blockingQueue.remove(); // NoSuchElementException
    }
}