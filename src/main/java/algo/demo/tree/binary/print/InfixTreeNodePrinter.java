package algo.demo.tree.binary.print;

import algo.demo.tree.binary.BinaryTree;

import java.util.function.Consumer;

public class InfixTreeNodePrinter extends TreeNodePrinter{
    @Override
    protected void doPrint(BinaryTree.TreeNode treeNode, Consumer<BinaryTree.TreeNode> recursiveFn) {
        recursiveFn.accept(treeNode.getLeft());
        System.out.println(treeNode);
        recursiveFn.accept(treeNode.getRight());

    }
}
