package algo.demo.tree.binary.print;

import algo.demo.tree.binary.BinaryTree;

import java.util.function.Consumer;

public class PostTreeNodePrinter extends TreeNodePrinter{
    @Override
    protected void doPrint(BinaryTree.TreeNode treeNode, Consumer<BinaryTree.TreeNode> recursiveFn) {
        recursiveFn.accept(treeNode.getLeft());
        recursiveFn.accept(treeNode.getRight());
        System.out.println(treeNode);
    }
}
