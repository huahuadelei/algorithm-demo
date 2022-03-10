package algo.demo.queue.simple;

public interface Queue<T> {

    /**
     *  添加数据
     */
    void add(T data);


    /**
     * 弹出数据
     */
    T pop();

}
