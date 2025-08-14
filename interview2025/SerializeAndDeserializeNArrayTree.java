package interview2025;

import java.util.*;
// serialize and deserialize an N Array tree.
public class SerializeAndDeserializeNArrayTree {


    /*

          1
       /  |  \
     2    3   4
   /  \          \
  5    6          7

1,2,5,|,6,|,|, 3,|,4,7,|,|
*/
    public static void main(String[] args) {
        Node node5 = new Node(5, null);
        Node node6 = new Node(6, null);
        Node node2 = new Node(2, List.of(node5, node6));
        Node node3 = new Node(3, null);
        Node node7 = new Node(7, null);
        Node node4 = new Node(4, List.of(node7));
        Node node1 = new Node(1, List.of(node2, node3, node4));

        String treeInStringForm = binaryTreeToString(node1);
        System.out.println(treeInStringForm);

        Node tree = stringToBinaryTree(treeInStringForm);

        System.out.println(tree.val);

    }

    public static String binaryTreeToString(Node root) {
        if( root == null) {
            return null;
        }

        StringBuilder treeBuilder = new StringBuilder();

        preOrderTraverseOfTree(root, treeBuilder);

        return treeBuilder.toString();
    }

    public static void preOrderTraverseOfTree(Node root, StringBuilder treeBuilder) {
        if (root.children == null || root.children.isEmpty()) {
            treeBuilder.append(root.val).append(",");
            treeBuilder.append("|,");
            return;
        }

        treeBuilder.append(root.val).append(",");

        for (Node chilNode : root.children) {
            preOrderTraverseOfTree(chilNode, treeBuilder);
        }

        treeBuilder.append("|,");
    }



    public static Node stringToBinaryTree(String s) {
        String[] nodeValues = s.split(",");
        int[] nodeValueIndex = {0};

        return preOrderTraverseOfString(nodeValues, nodeValueIndex);
    }

    public static Node preOrderTraverseOfString(String[] nodeValues,int[] nodeValueIndex) {

        if ("|".equals(nodeValues[nodeValueIndex[0]])) {
            nodeValueIndex[0]++;
            return null;
        }
        int currentValue = Integer.parseInt(nodeValues[nodeValueIndex[0]]);
        nodeValueIndex[0]++;

        Node child = null;
        List<Node> children = new ArrayList<>();
        do {
            child = preOrderTraverseOfString(nodeValues, nodeValueIndex);
            if (child != null) {
                children.add(child);
            }
        } while (child != null);

        return new Node(currentValue, children);

    }

    static class Node {
        int val;

        List<Node> children;

        Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }
}

