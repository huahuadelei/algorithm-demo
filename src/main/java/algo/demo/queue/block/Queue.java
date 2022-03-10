package algo.demo.queue.block;

public interface Queue<T> {

    /**
     *  添加数据
     */
    void add(T data) throws InterruptedException;


    /**
     * 弹出数据
     */
    T pop();

}
