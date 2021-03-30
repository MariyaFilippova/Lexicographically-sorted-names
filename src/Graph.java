import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Graph {
    Map<Character, Node> nodes = new HashMap<>();
    ArrayList<Character> entryPointsOfConnectedComponents = new ArrayList<>();
    int components;

    int connectedComponents() {
        components = nodes.size();
        int[] parents = new int[26];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
        for (Map.Entry<Character, Node> node: nodes.entrySet()) {
            for (Node child: node.getValue().children) {
                union(parents, child.value - 'a', node.getKey() - 'a');
            }
        }
        return components;
    }

    void union(int[] parents, int v, int u) {
        int parent_first = findParent(parents, u);
        int parent_second = findParent(parents, v);
        if (parent_first != parent_second) {
            components--;
            parents[parent_first] = parent_second;
        }
    }

    int findParent(int[] parents, int v) {
        if (parents[v] == v) {
            return v;
        }
        return parents[v] = findParent(parents, parents[v]);
    }

    boolean cycleInDirectedGraph() {
        return false;
    }

    void printAlphabet() {
        if (cycleInDirectedGraph()) {
            System.out.println("Impossible");
            return;
        }
        ArrayList<Character> alphabet = new ArrayList<>();
        boolean[] visited = new boolean[26];
        for (char root : this.entryPointsOfConnectedComponents) {
            if (nodes.containsKey(root)) {
                topologicalSort(nodes.get(root), visited, alphabet);
            }
        }
        for (int i = alphabet.size() - 1; i >= 0; i--) {
            System.out.print(alphabet.get(i) + " ");
        }
        for (int i = 0; i < 26; i++) {
            if (!visited[i]) {
                System.out.print((char) (i + 'a') + " ");
            }
        }
    }

    private void topologicalSort(Node root, boolean[] visited, ArrayList<Character> alphabet) {
        if (root == null || visited[root.value - 'a']) {
            return;
        }
        visited[root.value - 'a'] = true;
        for (int i = 0; i < root.children.size(); i++) {
            topologicalSort(root.children.get(i), visited, alphabet);
        }
        alphabet.add(root.value);
    }
}

class Node {
    Character value;
    ArrayList<Node> children;

    Node(Character character) {
        value = character;
        children = new ArrayList<>();
    }
}