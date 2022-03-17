package algo.demo.map;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * 利用hash表实现map集合
 * 1 使用hash表
 * 2 使用数组，涉及到扩容问题
 * 3 综合使用数组+链表的方式
 */
public class HashMap<K, V> implements Map<K, V> {

    private static final float CAPACITY_DIVISOR = 0.75F;
    private static final int MIN_CAP_SIZE = 10;

    private Node<K, V>[] elements;
    private int size;


    public HashMap(int initCap) {
        this.elements = new Node[Math.max(MIN_CAP_SIZE, initCap)];
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("assets [key] not null");
        }

        // 判断是否需要扩容
        checkAndExpanCap();

        // 获取Key的哈希值
        int hash = key.hashCode();
        int index = hash % elements.length;

        // 定位hash表
        Node<K, V> headNode = elements[index];

        // 判断是否出现hash碰撞以及key值已存在的情况
        if (headNode != null) {

            Node<K, V> check = null;
            for (Node<K, V> t = headNode; t != null; t = t.nextNode) {
                if (t.hash == hash && (t.key == key || t.key.equals(key))) {
                    check = t;
                    break;
                }
            }

            if (check != null) {
                V odd = check.value;
                check.value = value;
                return odd;
            }

        }

        // 创建新的Node
        Node<K, V> newNode = new Node<>(key, value, hash, headNode);

        elements[index] = newNode;


        size++;
        return headNode != null ? headNode.value : null;
    }


    /**
     * 检查需要扩容并扩容
     */
    private void checkAndExpanCap() {

        /*
            1 size占据数组总长度百分之75将进行扩容
            2 扩容到之前数组的两倍
            3 扩容之前先获取entryset到临时变量，扩容之后 将保存的entryset重新进行hash槽的插入
         */
        double capLimit = elements.length * 0.75;
        if (size < capLimit) {
            return;
        }

        Set<Node<K, V>> nodes = new HashSet<>();

        // 获取所有的entrySet
        ergodicEntrys((index, node) -> {

            if (elements[index] != null) {
                elements[index] = null;
            }

            nodes.add(node);
            node.nextNode = null;

        });


        // 扩容操作
        int capSize = elements.length << 1;
        Node<K, V>[] newTables = new Node[capSize];
        size = 0;
        elements = newTables;


        for (Node<K, V> node : nodes) {
            put(node.getKey(), node.getValue());
        }

    }


    @Override
    public V get(K key) {
        // 获取Key的哈希值
        int hash = key.hashCode();
        int index = hash % elements.length;

        // 定位hash表
        Node<K, V> headNode = elements[index];

        Node<K, V> check = null;

        if (headNode != null) {

            for (Node<K, V> t = headNode; t != null; t = t.nextNode) {
                if (t.hash == hash && (t.key == key || t.key.equals(key))) {
                    check = t;
                    break;
                }
            }
        }

        return check!=null?check.value:null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {

        Set<K> keySets = new HashSet<>();
        ergodicEntrys((index, node) -> {
            K key = node.getKey();
            keySets.add(key);
        });

        return keySets;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {

        Set<Map.Entry<K, V>> keySets = new HashSet<>();
        ergodicEntrys((index, node) -> {
            keySets.add(node);
        });

        return keySets;
    }


    /**
     * 遍历所有entrys，由acceptor来处理
     *
     * @param acceptor
     */
    private void ergodicEntrys(BiConsumer<Integer, Node<K, V>> acceptor) {
        for (int i = 0; i < elements.length; i++) {

            Node<K, V> element = elements[i];
            // 如果为null，说明该hash槽没有数据
            if (element == null) {
                continue;
            }

            // 遍历单向链表
            for (Node<K, V> node = element; node != null; ) {
                Node<K, V> nextNode = node.nextNode;

                acceptor.accept(i, node);

                node = nextNode;
            }
        }
    }


    static class Node<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private final int hash;
        private Node<K, V> nextNode;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?, ?> node = (Node<?, ?>) o;
            return hash == node.hash && Objects.equals(key, node.key) && Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value, hash);
        }

        public Node(K key, V value, int hash, Node<K, V> nextNode) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.nextNode = nextNode;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>(10);

        map.put("name", "张三");
        map.put("name", "李四");

        for (int i = 0; i < 10; i++) {
            map.put("name" + i, "张三" + i);
        }

        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            System.out.printf("k=%s,v=%s\n",key,value);
        }

        Set<String> keySet = map.keySet();
        System.out.println(Arrays.toString(keySet.toArray(new String[0])));

        for (String key : keySet) {
            System.out.printf("k=%s,v=%s\n",key,map.get(key));
        }
    }

}
