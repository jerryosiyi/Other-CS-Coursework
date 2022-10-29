import java.util.ArrayList;

public class AVLTree {
    AVLTreeNode root;
    // Note: you may define other variables here

    public AVLTree() {
        root = new AVLTreeNode(null, null, null);
    }

    public void createTestTree() {
        AVLTreeNode oneNode = new AVLTreeNode("1", null, null);
        AVLTreeNode twoNode = new AVLTreeNode("2", null, null);
        AVLTreeNode threeNode = new AVLTreeNode("3", null, null);
        AVLTreeNode fourNode = new AVLTreeNode("4", null, null);
        AVLTreeNode fiveNode = new AVLTreeNode("5", null, null);
        AVLTreeNode sixNode = new AVLTreeNode("6", null, null);
        AVLTreeNode sevenNode = new AVLTreeNode("7", null, null);

        root = fourNode;
        fourNode.left = twoNode;
        fourNode.right = sixNode;
        twoNode.left = oneNode;
        twoNode.right = threeNode;
        sixNode.left = fiveNode;
        sixNode.right = sevenNode;
    }

    public void print() {
        if(root == null)
            return;
        printFormat(root, "");
    }

    private void printFormat(AVLTreeNode current, String indent) {
        if (current != null) {
            System.out.printf("%s %s%n", indent, current.element);
            printFormat(current.left, indent + "  ");
            printFormat(current.right, indent + "  ");
        }
    }

    public boolean inTree(String e) {
        AVLTreeNode current = root;
        while(current!=null){
            if(current.element.equals(e)) return true;
            current = current.element.compareTo(e) > 0 ? current.left : current.right;
        }
        return false;
    }

    public void insert(String e) {
        AVLTreeNode parentNode = null;
        AVLTreeNode current = root;
        if(current == null){
            root = new AVLTreeNode(e, null, null);
            return;
        }
        while(current != null){
            if(current.element.equals(e)) return;
            if(current.element.compareTo(e) > 0){
                parentNode = current;
                current = current.left;
            }
            else{
                parentNode = current;
                current = current.right;
            }
        }

        if (parentNode.element.compareTo(e) > 0)
            parentNode.left = new AVLTreeNode(e, null, null);
        else
            parentNode.right = new AVLTreeNode(e, null, null);
    }

    public void insertBalanced(String e) {
        insert(e);
        ArrayList<AVLTreeNode> nodes = new ArrayList<>();
        addToNodes(nodes, root);
        root = arrayToTree(nodes, 0, nodes.size() - 1);
    }

    private void addToNodes(ArrayList<AVLTreeNode> nodeArray, AVLTreeNode node) {
        if(node != null){
            addToNodes(nodeArray, node.left);
            nodeArray.add(node);
            addToNodes(nodeArray, node.right);
        }
    }

    private AVLTreeNode arrayToTree(ArrayList<AVLTreeNode> nodeArray, int start, int end){
        if (start > end)
            return null;

        int middle = (start + end)/2;
        AVLTreeNode node = nodeArray.get(middle);

        node.left = arrayToTree(nodeArray, start, middle - 1);
        node.right = arrayToTree(nodeArray, middle + 1, end);

        return node;
    }
}
