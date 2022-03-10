package algo.demo.queue.simple;

public class LinkedQueue<T> implements Queue<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    private int maxLen;

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

        // 是否满
        if (isFull()) {
            throw new RuntimeException("队列已满");
        }

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
    }

    @Override
    public T pop() {
        if(isEmpty()){
            throw new RuntimeException("队列数据为空");
        }

        Node<T> head = this.first;
        this.first=this.first.next;
        head.next = null;

        size--;
        if(isEmpty()){
            last=null;
        }

        return head.data;
    }


    public boolean isFull() {
        return maxLen != -1 && size == maxLen;
    }

    public boolean isEmpty() {
        return first == null;
    }


}
