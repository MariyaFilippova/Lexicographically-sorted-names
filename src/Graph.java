
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * An object that stores information about the order of
 * letters in the generated alphabet.
 */
class Graph {

    /**
     * Maps char to node in the graph.
     */
    private final Map<Character, Node> nodes;

    public Map<Character, Node> getNodes() {
        return nodes;
    }

    Graph() {
        nodes = new HashMap<>();
    }

    /**
     * @return the number of connected components in the graph.
     */
    int connectedComponents() {
        int components = nodes.size();
        int[] parents = new int[Constants.ALPHABET_SIZE];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
        for (Map.Entry<Character, Node> node : nodes.entrySet()) {
            char value = node.getValue().getValue();
            ArrayList<Node> children = node.getValue().getChildren();
            for (Node child : children) {
                int v = value - 'a';
                int u = child.getValue() - 'a';
                if (!union(parents, v, u)) {
                    components--;
                }
            }
        }
        return components;
    }

    /**
     * Join two subsets into a single subset.
     *
     * @param parents array of ids of subsets.
     * @param v       first subset.
     * @param u       second subset.
     * @return if the subsets are already connected.
     */
    private boolean union(int[] parents, int v, int u) {
        int parentFirst = findParent(parents, u);
        int parentSecond = findParent(parents, v);
        if (parentFirst != parentSecond) {
            parents[parentFirst] = parentSecond;
            return false;
        }
        return true;
    }

    /**
     * Determine which subset a particular node is in.
     *
     * @param parents array of ids.
     * @param v       number of node.
     * @return id of a subset.
     */
    private int findParent(int[] parents, int v) {
        if (parents[v] == v) {
            return v;
        }
        parents[v] = findParent(parents, parents[v]);
        return parents[v];
    }

    /**
     * Sort nodes in the connected component
     * and determine if there is a cycle.
     *
     * @param root     node of the connected component.
     * @param visited  marker that node was seen.
     * @param color    shows that the node was entered.
     * @param alphabet generated alphabet.
     * @return if there is a cycle.
     */
    private boolean cycleInDirectedGraph(final Node root,
                                         boolean[] visited,
                                         boolean[] color,
                                         final ArrayList<Character> alphabet) {
        char value = root.getValue();
        ArrayList<Node> children = root.getChildren();
        if (color[value - 'a']) {
            return true;
        }
        if (visited[value - 'a']) {
            return false;
        }
        visited[value - 'a'] = true;
        color[value - 'a'] = true;
        for (Node node : children) {
            if (cycleInDirectedGraph(node, visited, color, alphabet)) {
                return true;
            }
        }
        alphabet.add(value);
        color[value - 'a'] = false;
        return false;
    }

    /**
     * Prints the alphabet or a error message
     * if it is impossible.
     */
    void printAlphabet() {
        ArrayList<Character> alphabet = new ArrayList<>();
        for (Node root : nodes.values()) {
            if (cycleInDirectedGraph(root, new boolean[Constants.ALPHABET_SIZE],
                    new boolean[Constants.ALPHABET_SIZE],
                    alphabet)) {
                System.out.println("Impossible");
                return;
            }
        }
        for (int i = alphabet.size() - 1; i >= 0; i--) {
            System.out.print(alphabet.get(i) + " ");
        }
        boolean[] visited = new boolean[Constants.ALPHABET_SIZE];
        for (int i = 0; i < Constants.ALPHABET_SIZE; i++) {
            if (!visited[i]) {
                System.out.print((char) (i + 'a') + " ");
            }
        }
    }
}

/**
 * An object that represents a single letter.
 */
class Node {

    /**
     * Letter.
     */
    private final char value;

    public char getValue() {
        return value;
    }

    /**
     * Nodes corresponding to the letters
     * that should be after current
     * in the generated alphabet.
     */
    private final ArrayList<Node> children;

    public ArrayList<Node> getChildren() {
        return children;
    }

    Node(char character) {
        value = character;
        children = new ArrayList<>();
    }
}