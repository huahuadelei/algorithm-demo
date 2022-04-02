package algo.demo.tree.binary.print;

import algo.demo.tree.binary.BinaryTree;

import java.util.function.Consumer;

public abstract class TreeNodePrinter {

    public void print(BinaryTree.TreeNode treeNode){
        if(treeNode == null){
            return ;
        }
        doPrint(treeNode,this::print);
    }

    protected abstract void doPrint(BinaryTree.TreeNode treeNode, Consumer<BinaryTree.TreeNode> recursiveFn);
}
