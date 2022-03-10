package algo.demo.queue.block;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组实现的简单队列
 *
 * @author MIBOOK
 */
public class ArrayQueue<T> implements Queue<T> {
    private Object[] element;
    private int comsumerIndex;
    private int providerIndex;
    private int size;


    private Lock lock = new ReentrantLock();
    private Condition empty = lock.newCondition();
    private Condition full = lock.newCondition();


    public ArrayQueue(int maxLeng) {
        this.element = new Object[maxLeng];
    }

    public void add(T data) {

        try {
            lock.lockInterruptibly();


            if (isFull()) {
                while (isFull()) {
                    full.await();
                }
            }

            enqueue(data);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private void enqueue(T data) {
        if (providerIndex == element.length) {
            providerIndex = 0;
        }

        this.element[providerIndex] = data;
        providerIndex++;
        size++;

        empty.signal();
    }

    public T pop() {
        try {
            lock.lockInterruptibly();

            long toNanos = TimeUnit.MILLISECONDS.toNanos(500);

            if (isEmpty()) {

                while (isEmpty()) {
                    if (toNanos <= 0) {
                        return null;
                    }
                    toNanos = empty.awaitNanos(toNanos);
                }
            }

            return dequeue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;

    }

    public T dequeue() {
        if (comsumerIndex == element.length) {
            comsumerIndex = 0;
        }

        T data = (T) element[comsumerIndex];
        this.element[comsumerIndex] = null;
        comsumerIndex++;
        size -= 1;

        full.signal();

        return data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return element.length == size;
    }


}
