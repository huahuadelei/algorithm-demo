package algo.demo.tree.binary;

/**
 * 初识二叉树，三种遍历顺序 ：
 * - 前序遍历：先遍历根，在遍历左边，再遍历右边
 * - 中序遍历：先遍历左边，再遍历根，再遍历右边
 * - 后序遍历：先遍历左边，再遍历右边，再遍历根
 */
public class SimpleBinaryTree {

    private Node root;


    public void add(int data) {
        Node node = new Node(data);
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }

    }

    public Node search(int data) {
        if (root == null) {
            return null;
        }
        return root.search(data);
    }

    public int height() {
        return this.root.height();
    }

    public boolean remove(int data) {
        if (root == null) {
            return false;
        }

        // 删除root节点
        if (root.data == data) {
            doRemove((Node) null, root);
            return true;
        }

        // 查找要删除的元素和它的父元素
        Node delNode = null;
        Node parentNode = null;

        delNode = root.search(data);

        if (delNode == null) {
            return false;
        }

        parentNode = doSearchParent(root, delNode);

        doRemove(parentNode, delNode);
        return true;
    }

    private Node doSearchParent(Node node, Node childNode) {
        if ((node.left != null && node.left == childNode) || (node.right != null && node.right == childNode)) {
            return node;
        }

        if (childNode.data < node.data && node.left != null) {
            return doSearchParent(node.left, childNode);
        } else if (childNode.data > node.data && node.right != null) {
            return doSearchParent(node.right, childNode);
        } else {
            return null;
        }

    }

    private void doRemove(Node parent, Node node) {
        // 没有左右子节点
        if (node.left == null && node.right == null) {

            // 说明删除的是一个根节点
            if (parent == null && node == root) {
                root = null;
            } else {
                // 说明被删除元素是parent的左子节点
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    //否则就是右子节点
                    parent.right = null;
                }

            }

            // 有两个子节点
        } else if (node.left != null && node.right != null) {

            int lHieght = node.left.height();
            int rHeight = node.right.height();

            // 左子树高或左右子树相等高度，从左边取一个最大元素
            if(lHieght >= rHeight){
                // 取最大值
                Node maxNode = maxDataNodeOf(node.left);

                Node maxParent = doSearchParent(root, maxNode);

                node.data = maxNode.data;

                doRemove(maxParent,maxNode);

            }else{
                // 从右子树取最小元素节点
                Node minNode = minDataNodeOf(node.right);
                Node minParent = doSearchParent(root, minNode);

                node.data = minNode.data;

                doRemove(minParent,minNode);
            }

            // 只有一个子节点
        } else {

            // 只有一个左节点
            if (node.left != null) {
                // 删除的是根 只有根节点没有父元素
                if (parent == null && node == root) {
                    root = node.left;
                } else {
                    if (parent.left == node) {
                        parent.left = node.left;
                    } else {
                        parent.right = node.left;
                    }
                }

                // 只有右子节点
            } else {

                if (parent == null && node == root) {
                    root = node.right;
                } else {
                    if (parent.left == node) {
                        parent.left = node.right;
                    } else {
                        parent.right = node.right;
                    }
                }
            }

        }


    }

    private Node minDataNodeOf(Node node) {
        if(node==null){
            return null;
        }

        if(node.left == null){
            return node;
        }
        return minDataNodeOf(node.left);
    }

    private Node maxDataNodeOf(Node node) {
        if(node==null){
            return null;
        }

        if(node.right==null){
            return node;
        }
        return maxDataNodeOf(node.right);
    }

    public void preOrder() {
        if (root != null) {
            doPreOrder(root);
        }
    }

    private void doPreOrder(Node node) {
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

    private void doMiddleOrder(Node node) {
        if (node == null) {
            return;
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

    private void doPostOrder(Node node) {
        if (node == null) {
            return;
        }

        doPostOrder(node.left);
        doPostOrder(node.right);
        System.out.println(node);
    }

    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }

        public Node search(int data) {

            Node nextSearce = null;

            if (data == this.data) {
                return this;
            } else if (data > this.data) {
                nextSearce = this.right;
            } else {
                nextSearce = this.left;
            }

            if (nextSearce != null) {
                return nextSearce.search(data);
            }
            return null;
        }

        public void add(Node node) {
            if (node == null) {
                return;
            }

            if (node.data < this.data) {
                if (this.left == null) {
                    this.left = node;
                } else {
                    this.left.add(node);
                }
            } else if (node.data > this.data) {
                if (this.right == null) {
                    this.right = node;
                } else {
                    this.right.add(node);
                }
            }
        }

        public int height() {
            return Math.max(left != null ? left.height() : 0, right != null ? right.height() : 0) + 1;
        }
    }

    public static void main(String[] args) {


        int[] arr = new int[]{4, 6, 1, 3, 2, 7, 9,8, 5};


        SimpleBinaryTree tree = new SimpleBinaryTree();
        for (int item : arr) {
            tree.add(item);
        }


        System.out.println("删除前=================>");
        tree.preOrder();

        System.out.println();
        System.out.println("删除后================>");
        tree.remove(3);
        tree.preOrder();

    }

}
