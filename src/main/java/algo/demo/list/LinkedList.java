package algo.demo.list;

import java.util.Iterator;

/**
 * 链表实现List
 */
public class LinkedList<T> implements List<T>,Iterable<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;


    @Override
    public void add(T data) {

        // 创建新的node节点
        Node<T> newNode = new Node<>(last, data, null);

        if (first == null) {
            first = newNode;
            last = newNode;
        } else {

            last.next = newNode;
            last = newNode;
        }

        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        Node<T> node = this.getNode(index);
        return node!=null?node.data:null;
    }

    @Override
    public T set(int index, T data) {
        Node<T> node = this.getNode(index);

        T odd = node.data;
        node.data = data;

        return odd;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(int index, T data) {

        // 检查索引越界
        checkIndexOutOfBounds(index);

        // 向后追加元素
        if (index == size) {
            this.add(data);
            return;
        }

        // 通过索引获取元素
        Node<T> node = getNode(index);
        Node<T> prev = node.prev;

        Node<T> newNode = new Node<>(prev, data, node);
        if(prev != null){
            prev.next = newNode;
        }
        node.prev = newNode;

        // 设置头节点为新元素
        if (node == first) {
            first = newNode;
        }
        size++;

    }

    // 索引获取元素
    private Node<T> getNode(int index) {

        //检查索引
        checkIndexOutOfBounds(index);

        // 计算一半的位置 进行折半查找
        int half = size >> 1;

        Node<T> node = null;
        // 折半查找
        if (index < half) {

            node = first;

            for (int i = 0; i < index; i++) {
                node = node.next;
            }

        } else {
            node = last;

            for (int i = size-1; i > index; i--) {
                node = node.prev;
            }
        }

        return node;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);

        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev != null) {
            prev.next = next;
        }

        if (next != null) {
            next.prev = prev;
        }

        node.next = null;
        node.prev = null;

        size--;

        return node.data;
    }

    private void checkIndexOutOfBounds(int index) {

        // 判断索引越界
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Itor();
    }

    public class Itor implements Iterator<T>{
        private Node<T> posNode = new Node<>(null,null,first);

        @Override
        public boolean hasNext() {
            return posNode.next != null;
        }

        @Override
        public T next() {
            Node<T> next = posNode.next;
            posNode = next;
            return next.data;
        }
    }


    private static class Node<T> {
        private Node<T> prev;
        private T data;
        private Node<T> next;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("i");
        list.add("love");
        list.add("you");

        list.add(0,"@");

        list.add(list.size(),"$");

        list.set(1,"I");

        list.remove(3);

        System.out.println("共有数据"+list.size());

        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            System.out.println(item);
        }


        System.out.println("===============迭代器便利=================");

        for (String item : (LinkedList<String>)list){
            System.out.println(item);
        }
    }

}
