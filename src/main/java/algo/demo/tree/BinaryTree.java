package algo.demo.tree;

/**
 * 初识二叉树，三种遍历顺序 ：
 * - 前序遍历：先遍历根，在遍历左边，再遍历右边
 * - 中序遍历：先遍历左边，再遍历根，再遍历右边
 * - 后序遍历：先遍历左边，再遍历右边，再遍历根
 */
public class BinaryTree<K, V> {

    private Node<K, V> root;

    public BinaryTree(Node<K, V> root) {
        this.root = root;
    }

    public void preOrder() {
        if (root != null) {
            doPreOrder(root);
        }
    }

    private void doPreOrder(Node<K, V> node) {
        if (node == null) {
            return;
        }
        System.out.println(node);

        doPreOrder(node.left);
        doPreOrder(node.right);
    }

    public void middleOrder() {
        if (root != null) {
            doMiddleOrder(root);
        }
    }

    private void doMiddleOrder(Node<K, V> node) {
        if(node == null){
            return ;
        }

        doMiddleOrder(node.left);
        System.out.println(node);
        doMiddleOrder(node.right);
    }

    public void postOrder() {
        if (root != null) {
            doPostOrder(root);
        }
    }

    private void doPostOrder(Node<K, V> node) {
        if(node == null){
            return ;
        }

        doPostOrder(node.left);
        doPostOrder(node.right);
        System.out.println(node);
    }

    public static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value, Node<K, V> left, Node<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {

        Node<Integer, String> l1 = new Node<Integer, String>(0, "李四", null, null);

        Node<Integer, String> r2 = new Node<Integer, String>(3, "马3", null, null);
        Node<Integer, String> r3 = new Node<Integer, String>(4, "王4", null, null);
        Node<Integer, String> r1 = new Node<Integer, String>(2, "李2", r2, r3);

        Node<Integer, String> root = new Node<Integer, String>(1, "张三", l1, r1);

        BinaryTree binaryTree = new BinaryTree(root);

        System.out.println("前序遍历");
        binaryTree.preOrder();

        System.out.println("中序遍历");
        binaryTree.middleOrder();

        System.out.println("后序遍历");
        binaryTree.postOrder();


    }

}
