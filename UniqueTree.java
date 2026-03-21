package A04SymbolTable;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 * Represents a binary tree with unique integer values. Provides various methods to
 * manipulate and analyze the tree structure, including doubling even numbers, counting
 * specific types of nodes, and calculating differences between values.
 * 
 * The tree data is represented as a list of integer arrays, where each array contains
 * the value of a node and the indices of its left and right children. For example:
 *
 * [node_value, left_child_index, right_child_index]
 *
 * The root node is at index 0. A left or right child index of null indicates that
 * the node does not have a corresponding child.
 * 
 * @author Hanseo + Joshua
 */
public class UniqueTree {

    private Node root;
    private Set<Integer> elements = new HashSet<>();  // To ensure uniqueness
    private int average = 0;
    private int averageCount = 0;
    int max = 0;
	int min = 0;

    // Inner class representing a node in the tree
    private class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    /**
     * Constructs a UniqueTree from a list of integer arrays.
     * Each array contains: [node_value, left_child_index, right_child_index]
     *
     * @param treeData the list representing the tree structure
     * @throws IllegalArgumentException if the tree contains duplicate values
     */
    public UniqueTree(List<Integer[]> treeData) {
        if (treeData == null || treeData.isEmpty()) {
            throw new IllegalArgumentException("Tree data cannot be null or empty.");
        }
        this.root = buildTree(treeData, 0);  // Start building from the root (index 0)
    }

    // Helper method to recursively build the tree from the list
    private Node buildTree(List<Integer[]> data, int index) {
        if (index == -1 || index >= data.size() || data.get(index) == null) {
            return null;
        }

        Integer[] nodeData = data.get(index);
        int value = nodeData[0];

        // Check for uniqueness
        if (!elements.add(value)) {
            throw new IllegalArgumentException("Duplicate value found: " + value);
        }

        Node node = new Node(value);

        // Build the left and right subtrees using the provided indices
        node.left = buildTree(data, nodeData[1] != null ? nodeData[1] : -1);
        node.right = buildTree(data, nodeData[2] != null ? nodeData[2] : -1);

        return node;
    }

    /**
     * Doubles the value of all even numbers in the tree.
     */
    public void doubleEvenNumbers() {
        doubleEvenNumbers(root);
    }

    private void doubleEvenNumbers(Node node) {
        if (node == null) {
            return;
        }

        if (node.val % 2 == 0) {
            node.val = node.val * 2;
        }

        doubleEvenNumbers(node.left);
        doubleEvenNumbers(node.right);
    }

    /**
     * Calculates the sum of all right children (excluding the root).
     * @return the sum of all right children
     */
    public int sumOfRightChildren() {
        return sumOfRightChildren(root);
    }

    private int sumOfRightChildren(Node node) {
        if (node == null) {
            return 0;
        }

        int sum = 0;

        if (node.right != null) {
            sum += node.right.val;
        }

        sum += sumOfRightChildren(node.left);
        sum += sumOfRightChildren(node.right);

        return sum;
    }

    /**
     * Counts how many nodes have at least one child that is a leave.
     * 
     * @return the count of nodes with all children as leaves
     */
    public int countLeafParents() {
        return countLeafParents(root);
    }

    private int countLeafParents(Node node) {
        if (node == null) {
            return 0;
        }

        boolean leftLeaf = node.left != null && node.left.left == null && node.left.right == null;
        boolean rightLeaf = node.right != null && node.right.left == null && node.right.right == null;

        int count = 0;
        if (leftLeaf || rightLeaf) {
            count = 1;
        }

        count += countLeafParents(node.left);
        count += countLeafParents(node.right);

        return count;
    }
    /**
     * Calculates the average of all odd numbers in the tree.
     * 
     * @return the average of all odd numbers, or 0 if there are no odd numbers
     */
    public double averageOfOddNumbers() {
    	averageOfOddNumbers(root);
        return average / averageCount;
    }
    
    // overloaded method
    private void averageOfOddNumbers(Node root){ // Each child of a tree is a root of its subtree.
    	if (root.left != null) {
    		averageOfOddNumbers(root.left);
    	}
    	if(root.val % 2 != 0) {
    		average += root.val;
    		averageCount += 1;
    	}
	    if (root.right != null) {
	    	averageOfOddNumbers(root.right);
	    }
    }

    /**
     * Finds the difference between the largest and smallest elements in the tree.
     * 
     * @return the difference between the largest and smallest values
     */
    public int greatestDifference() {
    	greatestDifference(root);
        return max-min;
    }
    
    // overloaded method
    private void greatestDifference(Node root){ // Each child of a tree is a root of its subtree.
    	if(root.val > max || max == 0) {
    		max = root.val;
    	}
    	if(root.val < min || min == 0) {
    		min = root.val;
    	}
    	if(root.left != null) {
    		greatestDifference(root.left);
    	}
	    if(root.right != null) {
	    	greatestDifference(root.right);
	    }
    }

}

