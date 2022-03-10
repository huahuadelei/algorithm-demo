package algo.demo.queue.block;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedQueue<T> implements Queue<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    private int maxLen;

    private Lock lock = new ReentrantLock();
    private Condition empty = lock.newCondition();
    private Condition full = lock.newCondition();


    public LinkedQueue(int maxLen) {
        this.maxLen = maxLen;
    }

    public LinkedQueue() {
        this(-1);
    }

    // 数据节点
    static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }


    @Override
    public void add(T data) {

        lock.lock();
        try {
            long toNanos = TimeUnit.MILLISECONDS.toNanos(500);
            // 是否满
            while (isFull()) {
                if(toNanos<=0){
                    throw new RuntimeException("空间满了");
                }
                toNanos = full.awaitNanos(toNanos);
            }

            enqueue(data);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    private void enqueue(T data) {
        // 新的节点
        Node<T> newNode = new Node<T>(data, null);

        // 第一次插入数据
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            // 非第一次插入数据
            last.next = newNode;
            last = newNode;
        }

        size++;

        empty.signal();
    }

    @Override
    public T pop() {

        lock.lock();
        try {
            long toNanos = TimeUnit.MILLISECONDS.toNanos(500);

            while (isEmpty()) {
                if(toNanos<=0){
                    return null;
                }
                try {
                    toNanos = empty.awaitNanos(toNanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            Node<T> head = dequeue();
            return head.data;
        } finally {
            lock.unlock();
        }

    }

    private Node<T> dequeue() {

        Node<T> head = this.first;
        this.first = this.first.next;
        head.next = null;

        size--;
        if (isEmpty()) {
            last = null;
        }

        full.signal();

        return head;

    }


    public boolean isFull() {
        return maxLen != -1 && size == maxLen;
    }

    public boolean isEmpty() {
        return first == null;
    }


}
