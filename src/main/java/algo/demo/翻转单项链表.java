package algo.demo;

import java.util.Iterator;

public class 翻转单项链表 {

    public static void main(String[] args) {

        LikedTable<Integer> likedTable = new LikedTable<>();
        likedTable.add(1);
        likedTable.add(2);
        likedTable.add(3);
        likedTable.add(4);
        likedTable.add(5);
        likedTable.add(6);

        System.out.println("遍历单向链表");
        for (Integer item : likedTable) {
            System.out.println(item);
        }


//        likedTable.reverse();
        likedTable.reverse2();

        System.out.println("翻转完毕遍历");
        for (Integer item : likedTable) {
            System.out.println(item);
        }


    }

    static class LikedTable<T> implements Iterable<Integer> {

        private Node<T> first;
        private int size;

        public void add(T ele) {

            first = new Node<>(ele, first);

            size++;
        }

        @Override
        public Iterator<Integer> iterator() {
            return (Iterator<Integer>) new Itor<T>(first);
        }

        /**
         * 使用while循环翻转单项链表
         */
        public void reverse() {
            // 将node设置为第二个节点
            Node<T> node = first.next;

            // 设置first的next为空
            first.next=null;

            while (node != null) {
                // 临时保存
                Node<T> temporary = node.next;

                node.next = first;
                first = node;

                node = temporary;
            }
        }


        /**
         * 使用递归法翻转单向链表
         */
        public void reverse2() {
            Node<T> node = first.next;
            first.next = null;

            doReverse2(node);
        }

        private void doReverse2(Node<T> node) {

            // 递归出口
            if(node == null){
                return ;
            }

            Node<T> temporary = node.next; // or null

            node.next=first;
            first=node;

            doReverse2(temporary);
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
