package algo.demo.list;

public class RingLinked<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;


    static class Node<T>{
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    public void add(T item){
        Node<T> node = new Node<>(item, null);
        if(head == null){
            head = node;
        }

        if(tail != null){
            tail.next = node;
        }

        node.next=head;
        tail = node;

        size++;
    }

    public static void main(String[] args) {

        int[] arr = {1,3,5,7,9,0};

        RingLinked<Integer> ringLinked = new RingLinked<>();
        for (int item : arr) {
            ringLinked.add(item);
        }

        System.out.println(ringLinked);
    }
}
