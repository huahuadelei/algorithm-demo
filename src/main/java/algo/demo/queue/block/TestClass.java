package algo.demo.queue.block;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TestClass {

    static volatile boolean runState = true;

    public static void main(String[] args) throws InterruptedException {
//        Queue<Integer> queue = new ArrayQueue<>(3);
        Queue<Integer> queue = new LinkedQueue<>(3);

        CountDownLatch downLatch1 = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            downLatch1.countDown();

            while (runState) {
                Integer pop = queue.pop();
                if(pop != null){
                    System.out.println(Thread.currentThread().getName() + "获取到数据==>" + pop);
                }else{
                    System.out.println(Thread.currentThread().getName() + "未获取到数据" );
                }
            }

        });
        thread.setDaemon(true);
        thread.setName("消费者线程");
        thread.start();

        downLatch1.await();

        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        TimeUnit.MILLISECONDS.sleep(5);
        runState=false;


        thread.join();
        System.out.println("结束");
    }

}
