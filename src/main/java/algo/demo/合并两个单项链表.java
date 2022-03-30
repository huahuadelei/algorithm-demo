package algo.demo;

import java.util.Iterator;

public class 合并两个单项链表 {

    public static void main(String[] args) {

        LikedTable<Integer> likedTable = new LikedTable<>();
        likedTable.add(1);
        likedTable.add(2);
        likedTable.add(5);
        likedTable.add(6);

        LikedTable<Integer> likedTable2 = new LikedTable<>();
        likedTable2.add(3);
        likedTable2.add(4);


        System.out.println("链表1");
        for (Integer item : likedTable) {
            System.out.println(item);
        }

        System.out.println("链表2");
        for (Integer item : likedTable2) {
            System.out.println(item);
        }


        // 合并两个链表
        Node<Integer> node1 = likedTable.getLinkedNodes();
        Node<Integer> node2 = likedTable2.getLinkedNodes();
        LikedTable<Integer> mergeTable = LikedTable.merge(node1, node2);

        System.out.println("合并");

        for (Integer integer : mergeTable) {

            System.out.println(integer);
        }


    }

    static class LikedTable<T> implements Iterable<T> {

        private Node<T> first;
        private Node<T> last;

        public void add(T ele) {

            Node<T> newNode = new Node<>(ele, null);

            if (first == null) {
                first = newNode;
            }else{
                last.next = newNode;
            }

            last = newNode;
        }

        @Override
        public Iterator<T> iterator() {
            return (Iterator<T>) new Itor<T>(first);
        }

        /**
         * 使用while循环翻转单项链表
         */
        public void reverse() {
            // 将node设置为第二个节点
            Node<T> node = first.next;

            // 设置first的next为空
            first.next = null;

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
            if (node == null) {
                return;
            }

            Node<T> temporary = node.next; // or null

            node.next = first;
            first = node;

            doReverse2(temporary);
        }

        public static  <T> LikedTable<T> merge(Node<T> node1,Node<T> node2) {
            LikedTable<T> newTable = new LikedTable<>();

            while(node1!=null && node2 != null){
                if((Integer)node1.data<(Integer)node2.data){
                    newTable.add(node1.data);
                    node1=node1.next;
                }else{
                    newTable.add(node2.data);
                    node2=node2.next;
                }
            }

            while (node2!=null){
                newTable.add(node2.data);
                node2=node2.next;
            }
            while (node1!=null){
                newTable.add(node1.data);
                node1=node1.next;
            }

            return newTable;
        }

        public Node<T> getLinkedNodes() {
            return first;
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
