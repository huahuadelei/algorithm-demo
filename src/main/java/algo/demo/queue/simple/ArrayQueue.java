package algo.demo.queue.simple;

/**
 *  数组实现的简单队列
 * @author MIBOOK
 */
public class ArrayQueue<T> implements Queue<T> {
    private Object[] element;
    private int comsumerIndex;
    private int providerIndex;
    private int size;

    public ArrayQueue(int maxLeng) {
        this.element = new Object[maxLeng];
    }

    public void add(T data) {
        if (isFull()) {
            throw new RuntimeException("队列已满");
        }

        if (providerIndex == element.length) {
            providerIndex = 0;
        }

        this.element[providerIndex] = data;
        providerIndex++;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("队列中无数据");
        }

        if (comsumerIndex == element.length) {
            comsumerIndex = 0;
        }

        T data = (T) element[comsumerIndex];
        this.element[comsumerIndex] = null;
        comsumerIndex++;
        size -= 1;

        return data;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return element.length == size;
    }


}
