package algo.demo.list;

/**
 * 数组实现List
 * 1 使用数组实现List涉及到扩容问题，因为数组长度不可变，而要实现的List长度是可变的，所以中间会有个扩种操作
 * 2 扩容1.5倍
 */
public class ArrayList<T> implements List<T> {

    private static int MIN_CAPACITY_SIZE = 10;

    private Object[] elements;
    private int size;


    private int getCapacitySize(int cap) {
        return Math.max(cap, MIN_CAPACITY_SIZE);
    }

    public ArrayList(int initLen) {
        this.elements = new Object[getCapacitySize(initLen)];
    }

    public ArrayList() {
        this(MIN_CAPACITY_SIZE);
    }

    @Override
    public void add(T data) {
        add(size, data);
    }

    private void capInternal(int minCapSize) {
        // 最小扩容大小不能比10小
        int capacitySize = getCapacitySize(minCapSize);

        // 扩容的大小为原来1.5倍
        int currentCapSize = elements.length + (elements.length >> 1);

        // 如果当前扩容 比 最小扩容要小，使用最小扩容
        if (currentCapSize < capacitySize) {
            currentCapSize = capacitySize;

            // 如果当前扩容 大于 int最大值 使用int最大值
        } else if (currentCapSize > Integer.MAX_VALUE) {

            // 检查当前数据量等于int最大值，则抛出异常
            if (elements.length == Integer.MAX_VALUE) {
                throw new OutOfMemoryError("集合容量超出Int最大值");
            }

            currentCapSize = Integer.MAX_VALUE;
        }


        elements = arrayCopyOf(elements, currentCapSize);
    }

    private Object[] arrayCopyOf(Object[] elements, int newArrayLen) {
        Object[] newInstance = new Object[newArrayLen];
        System.arraycopy(elements, 0, newInstance, 0, elements.length);
        return newInstance;
    }


    /**
     * 判断是否需要扩容
     */
    private boolean isCap() {
        return size == elements.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {


        // 检查索引越界
        checkIndexOutOfBounds(index);

        return (T) elements[index];
    }

    @Override
    public T set(int index, T data) {

        // 检查索引越界
        checkIndexOutOfBounds(index);


        Object oll = elements[index];
        elements[index] = data;

        return (T) oll;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    void checkIndexOutOfBounds(int index) {

        // 判断索引越界
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

    }

    @Override
    public void add(int index, T data) {

        // 检查索引越界
        checkIndexOutOfBounds(index);

        // 1 每次添加检查是否需要扩容
        if (isCap()) {
            capInternal(size + 1);
        }

        // 判断添加元素是否向数组中间插入，而不是向尾部加入。需要进行移动
        if (index < size) {
            moveIndexPostInternal(index);
        }

        // 向数组中添加数据
        elements[index] = data;
        size++;
    }

    private void moveIndexPostInternal(int index) {
        System.arraycopy(elements, index, elements, index + 1, size - index);
    }

    @Override
    public T remove(int index) {

        // 检查索引越界
        checkIndexOutOfBounds(index);

        //保存元数据
        Object element = elements[index];

        // 计算需要移动的数据
        int movNum = size - 1 - index;

        // 向前移动数据
        if (movNum > 0) {
            System.arraycopy(elements, index + 1, elements, index, movNum);
        }

        elements[--size] = null;
        return (T) element;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(i+1);
        }

        list.set(9,100);

        list.add(2,200);

        list.remove(1);

        System.out.printf("共有数据%s,便利如下\n",list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%s\t",list.get(i));
        }
        System.out.println();

    }
}

