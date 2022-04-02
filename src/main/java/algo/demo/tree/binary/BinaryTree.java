package algo.demo.tree.binary;

import algo.demo.tree.binary.print.PrintMode;
import algo.demo.tree.binary.print.TreeNodePrinter;
import algo.demo.tree.binary.print.TreeNodePrinterSelector;

public class BinaryTree {

    private TreeNode root;

    public void add(int data) {
        TreeNode newNode = new TreeNode(data);
        if (root == null) {
            root = newNode;
        } else {
            addNode(root, newNode);
        }
    }

    public void printTreeNode(PrintMode mode) {
        if (root == null) {
            return;
        }

        TreeNodePrinter printer = TreeNodePrinterSelector.select(mode);
        printer.print(root);
    }

    public TreeNode search(int data) {
        if (root == null) {
            return null;
        }

        return doSearch(root, data);
    }

    private TreeNode doSearch(TreeNode node, int search) {
        if (node == null) {
            return null;
        }

        if (search < node.data) {
            return doSearch(node.left, search);
        } else if (search > node.data) {
            return doSearch(node.right, search);
        } else {
            return node;
        }
    }

    public void remove(int data) {
        if (root == null) {
            return;
        }

        /**
         * 1.删除的是一个叶子节点 （没有左右子树）
         *   - 直接删除该元素即可，如果是根节点 设置root=null,如果是非根节点，设置parent指向该元素的指针为null
         *
         * 2.删除的元素包含左右两个子树
         *   - 多种方案
         *    1) 从左子树取最大的值替换该值（左子向右找到right为null的元素就是最大值），删除原来位置上的元素（可递归调用）
         *    2） 从右子树找最小值
         *    3） 判断哪边树高度高，从那边取值
         *
         * 3.删除的元素只有一个子树
         *  -如果删除的元素存在一个子树，将子树的元素给与当前删除元素的父元素
         */

        TreeNode delNode = search(data);
        if (delNode == null) {
            return;
        }

        TreeNode parent = getTreeNodeParent(root, delNode);

        removeTreeNodeOfParent(parent, delNode);
    }

    private void removeTreeNodeOfParent(TreeNode parent, TreeNode delNode) {
        //第一种情况 删除叶子节点元素

        if (delNode.left == null && delNode.right == null) {

            // 删除没有子元素的节点（叶子节点）
            removeNoChildNode(parent, delNode);
        } else if (delNode.left != null && delNode.right != null) {

            //删除有两个元素的节点
            removeAllChildNode(parent, delNode);
        } else {

            removeOneChildNode(parent, delNode);
        }
    }

    // 删除的是一个存在左右子元素的元素
    private void removeAllChildNode(TreeNode parent, TreeNode delNode) {

        // root节点
        TreeNode maxTreeNode = getMaxTreeNode(delNode.left);
        TreeNode maxParentNode = getTreeNodeParent(delNode, maxTreeNode);

        delNode.data = maxTreeNode.data;

        removeTreeNodeOfParent(maxParentNode,maxTreeNode);
    }

    private TreeNode getMaxTreeNode(TreeNode node) {
        if (node == null) {
            return null;
        }

        if (node.right == null) {
            return node;
        }
        return getMaxTreeNode(node.right);
    }

    // 只有一个子元素
    private void removeOneChildNode(TreeNode parent, TreeNode delNode) {

        // 删除的是根元素
        if (parent == null) {
            root = delNode;
        } else {

            if (parent.left == delNode) {
                if (delNode.left != null) {
                    parent.left = delNode.left;
                } else {
                    parent.left = delNode.right;
                }
            } else {
                if (delNode.left != null) {
                    parent.right = delNode.left;
                } else {
                    parent.right = delNode.right;
                }
            }
        }
    }

    // 没有子元素
    private void removeNoChildNode(TreeNode parent, TreeNode delNode) {
        if (parent == null) {
            root = null;
        } else {
            if (parent.left == delNode) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
    }


    private TreeNode getTreeNodeParent(TreeNode parentNode, TreeNode childNode) {
        if (childNode == root) {
            return null;
        }

        if ((parentNode.left != null && parentNode.left == childNode) || (parentNode.right != null && parentNode.right == childNode)) {
            return parentNode;
        }

        if (childNode.data < parentNode.data) {
            return getTreeNodeParent(parentNode.left, childNode);
        } else {
            return getTreeNodeParent(parentNode.right, childNode);
        }
    }


    protected void addNode(TreeNode node, TreeNode newNode) {

        // 向左边添加
        if (newNode.data < node.data) {

            // 左子树为空说明就是要添加的位置
            if (node.left == null) {
                node.left = newNode;
            } else {

                // 左子树不为空 说明需要将新元素继续向左查询插入的位置
                addNode(node.left, newNode);
            }

        } else {

            //向右边添加
            if (node.right == null) {
                node.right = newNode;
            } else {
                addNode(node.right, newNode);
            }

        }
    }

    public static class TreeNode {
        protected int data;
        //        private TreeNode parent;
        protected TreeNode left;
        protected TreeNode right;

        public TreeNode(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "data=" + data +
                    '}';
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 4, 2, 7, 9, 8, 3,5};

        BinaryTree avlTree = new BinaryTree();
        for (int item : arr) {
            avlTree.add(item);
        }

        avlTree.printTreeNode(PrintMode.PRE);

        TreeNode search = avlTree.search(12);
        System.out.println("\n===========>" + search);

        System.out.println("\n====================>");
        avlTree.remove(4);
        avlTree.printTreeNode(PrintMode.PRE);

    }
}
