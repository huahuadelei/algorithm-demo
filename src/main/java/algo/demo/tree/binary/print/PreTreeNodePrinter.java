package algo.demo.tree.binary.print;

import algo.demo.tree.binary.BinaryTree;

import java.util.function.Consumer;

public class PreTreeNodePrinter extends TreeNodePrinter{
    @Override
    protected void doPrint(BinaryTree.TreeNode treeNode, Consumer<BinaryTree.TreeNode> recursiveFn) {
        System.out.println(treeNode);
        recursiveFn.accept(treeNode.getLeft());
        recursiveFn.accept(treeNode.getRight());
    }
}
