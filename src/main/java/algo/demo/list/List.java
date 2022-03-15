package algo.demo.list;

public interface List<T> {

    void add(T data);

    int size();

    T get(int index);

    T set(int index,T data);

    boolean isEmpty();

    // 有难度的函数
    void add(int index,T data); // 插入数据到指定索引，原来位置上的数据后移
    T remove(int index); // 删除集合中的数据，数组元素重新移动
}
