package algo.demo.tree.avl;

public class AVLTree {

    private AVLNode root;

    public void add(int data) {
        AVLNode avlNode = new AVLNode(data, null);
        if (root == null) {
            root = avlNode;
        } else {
            if (addNode(root, avlNode)) {
                // 检查并旋转树进行调整
                checkAndRegulateTree(avlNode);
            }
        }
    }

    private void checkAndRegulateTree(AVLNode avlNode) {
        if (avlNode == null) {
            return;
        }

        // 左右子树的平衡因子  left.height - right.height
        int balanceFactor = getBalanceFactor(avlNode.left, avlNode.right);

        // 左侧书高 需要右旋
        if (balanceFactor > 1) {

            // 检查左边的元素是否需要调整，比如需要
            AVLNode left = avlNode.left;
            //说明左边节点的右子树高 需要先左旋  然后才能将 avlNode 节点进行右旋
            if (getBalanceFactor(left.left, left.right) <= -1) {
                System.out.println(" -- 发生了先左旋，再右旋的场景 <==> " + left.toString());
                leftRotate(left);
            }

            System.out.println("需要右旋 ==> " + avlNode.toString());
            rightRotate(avlNode);

            //右侧树高  需要左旋
        } else if (balanceFactor < -1) {


            AVLNode right = avlNode.right;

            //说明右边节点的左子树高 需要先右旋  然后才能将 avlNode 节点进行左旋
            if (getBalanceFactor(right.left, right.right) >= 1) {
                rightRotate(right);
                System.out.println(" -- 发生了先右旋，再左旋的场景 <==> " + right.toString());
            }

            System.out.println("需要左旋 <== " + avlNode.toString());
            leftRotate(avlNode);
        }

        // 递归检查父节点 直到root节点
        checkAndRegulateTree(avlNode.parent);
    }

    // 右旋转
    private void rightRotate(AVLNode avlNode) {
        AVLNode parent = avlNode.parent;
        boolean left = false;
        if (parent != null) {
            left = parent.left == avlNode;
        }

        /*
            1 创建新节点 值为左节点的值
            2 将新建节点的左节点设置为 左节点的左节点
            3 将新建节点的右节点设置为当前需要右旋的节点 （avlNode）

            4 将当前旋转的节点的左节点设置为 左节点的右节点（avlNode.left.right）
            5 判断旋转节点的父节点是否为null，如果为空说明是根节点，直接将root指向它，如果不为空 将parent对应子树指针 指向它
         */

        // 新建节点
        AVLNode newNode = new AVLNode(avlNode.left.data, parent);

        // 处理左边
        newNode.left = avlNode.left.left;
        if (avlNode.left.left != null) {
            avlNode.left.left.parent = newNode;
        }

        // 处理右边
        newNode.right = avlNode;
        avlNode.parent = newNode;
        avlNode.left = avlNode.left.right;

        if (avlNode.left != null) {
            avlNode.left.parent = avlNode;
        }

        // 如果parent为null 直接将新节点赋值给根
        if (parent == null) {
            root = newNode;
        } else {

            if (left) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }

    }

    // 左旋转
    private void leftRotate(AVLNode avlNode) {

        // 获取需要旋转的节点的父节点
        AVLNode parent = avlNode.parent;

        boolean left = false;
        if (parent != null) {
            left = parent.left == avlNode;
        }

         /*
            1 创建新节点 值为右子节点的值
            2 将新建节点的右节点设置为 右节点的右节点
            3 将新建节点的左节点设置为当前需要右旋的节点 （avlNode）

            4 将当前旋转的节点的右节点设置为 右节点的左节点（avlNode.right.left）
            5 判断旋转节点的父节点是否为null，如果为空说明是根节点，直接将root指向它，如果不为空 将parent对应子树指针 指向它
         */

        // 创建新节点
        AVLNode newNode = new AVLNode(avlNode.right.data, parent);

        // 处理右边
        newNode.right = avlNode.right.right;
        if (avlNode.right.right != null) {
            avlNode.right.right.parent = newNode;
        }


        // 处理左边
        newNode.left = avlNode;
        avlNode.parent = newNode;
        avlNode.right = avlNode.right.left;

        if (avlNode.right != null) {
            avlNode.right.parent = avlNode;
        }

        if (parent == null) {
            root = newNode;
        } else {
            if (left) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
    }

    private int getBalanceFactor(AVLNode left, AVLNode right) {
        return getHeight(left) - getHeight(right);
    }

    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(node.left == null ? 0 : getHeight(node.left), node.right == null ? 0 : getHeight(node.right)) + 1;
    }

    public void remove(int data) {
        if (root == null) {
            return;
        }
        delNode(root, data);
    }

    private void delNode(AVLNode node, int data) {
        if (node == null) {
            return;
        }

        if (data < node.data) {
            delNode(node.left, data);
        } else if (data > node.data) {
            delNode(node.right, data);
        } else {

            // 开始删除

            // 情况1 删除叶子节点
            if (node.left == null && node.right == null) {
                // 删除根节点
                if (node == root) {
                    root = null;
                } else {

                    AVLNode parent = node.parent;

                    if (parent.left == node) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }

                    checkAndRegulateTree(parent);
                }

            } else if (node.left != null && node.right != null) {
                // 情况2 左右子树都存在 从左边取最大值
                AVLNode maxNode = getMaxNode(node.left);

                // 将最大值放到被删除的元素节点上
                node.data = maxNode.data;

                // 删除最大值
                delNode(node.left, maxNode.data);
            } else {

                // 只有一个子树
                // 说明只有一个左子树
                if (node.left != null) {
                    if (node == root) {
                        root = node.left;
                    } else {
                        AVLNode parent = node.parent;

                        // 是父节点的左子节点
                        if (parent.left == node) {
                            parent.left = node.left;
                        } else {
                            parent.right = node.left;
                        }

                        node.left.parent = parent;

                        checkAndRegulateTree(parent);
                    }
                } else {

                    if (node == root) {
                        root = node.right;
                    } else {
                        AVLNode parent = node.parent;

                        if (parent.left == node) {
                            parent.left = node.right;
                        } else {
                            parent.right = node.right;
                        }

                        node.right.parent = parent;

                        checkAndRegulateTree(parent);

                    }

                }

            }

        }
    }

    private AVLNode getMaxNode(AVLNode node) {
        if (node == null) {
            return null;
        }
        if (node.right == null) {
            return node;
        }
        return getMaxNode(node.right);
    }

    private boolean addNode(AVLNode node, AVLNode avlNode) {
        if (node == null) {
            return false;
        }

        if (avlNode.data < node.data) {
            if (node.left == null) {
                node.left = avlNode;
                avlNode.parent = node;
                return true;
            } else {
                return addNode(node.left, avlNode);
            }
        } else {
            if (node.right == null) {
                node.right = avlNode;
                avlNode.parent = node;
                return true;
            } else {
                return addNode(node.right, avlNode);
            }
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    public void preOrder(AVLNode avlNode) {
        if (avlNode == null) {
            return;
        }
        System.out.println(avlNode);
        preOrder(avlNode.left);
        preOrder(avlNode.right);
    }

    static class AVLNode {
        private int data;
        private AVLNode parent;
        private AVLNode left;
        private AVLNode right;

        @Override
        public String toString() {
            return "AVLNode{" +
                    "data=" + data +
                    '}';
        }

        public AVLNode(int data, AVLNode parent) {
            this.data = data;
            this.parent = parent;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public AVLNode getParent() {
            return parent;
        }

        public void setParent(AVLNode parent) {
            this.parent = parent;
        }

        public AVLNode getLeft() {
            return left;
        }

        public void setLeft(AVLNode left) {
            this.left = left;
        }

        public AVLNode getRight() {
            return right;
        }

        public void setRight(AVLNode right) {
            this.right = right;
        }
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

//        int[] arr = new int[]{4, 5, 2, 3, 1, 6, 8, 9, 0, 7};
//        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr = new int[]{5, 4, 3, 2, 1, 0, 7, 8, 6};

        System.out.println("===============添加操作================");
        for (int item : arr) {

            avlTree.add(item);
        }
        avlTree.preOrder();


        System.out.println("============删除操作===============");
        avlTree.remove(0);
        avlTree.preOrder();
    }
}
