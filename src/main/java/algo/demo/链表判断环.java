package algo.demo;

import java.util.Iterator;

public class 链表判断环 {

    public static void main(String[] args) {

        Node<Integer> node5 = new Node<>(5, null);
        Node<Integer> node4 = new Node<>(4, node5);
        Node<Integer> node3 = new Node<>(3, node4);
        Node<Integer> node2 = new Node<>(2, node3);
        Node<Integer> node = new Node<>(1, node2);

//        node5.next = node;

        LikedTable<Integer> likedTable = new LikedTable<>();
        likedTable.setRoot(node);
        System.out.println(likedTable.hasRing());



    }

    static class LikedTable<T> implements Iterable<Integer> {

        private Node<T> first;

        public void setRoot(Node<T> root) {
            this.first = root;
        }

        @Override
        public Iterator<Integer> iterator() {
            return (Iterator<Integer>) new Itor<T>(first);
        }

        public boolean hasRing() {

            Node<T> n = first;
            Node<T> nn = first.next;
            while (n != null && nn != null) {

                if (n == nn) {
                    return true;
                }

                n = n.next;

                nn = nn.next;
                if (nn!=null) {
                    nn=nn.next;
                }
            }


            return false;
        }


        static class Itor<T> implements Iterator<T> {
            private Node<T> temp;

            public Itor(Node<T> root) {
                temp = root;
            }

            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public T next() {
                Node<T> tempL = this.temp;

                this.temp = temp.next;
                return tempL.data;
            }
        }
    }

    static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}
