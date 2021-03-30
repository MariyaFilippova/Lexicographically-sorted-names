import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Trie {
    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    void insert(String key, Graph graph) {
        TrieNode start = root;
        int n = key.length();
        for (int i = 0; i < n; i++) {
            char c = key.charAt(i);
            char ch = start.characterBefore;
            if (!graph.nodes.containsKey(c)) {
                graph.nodes.put(c, new Node(c));
            }
            if (ch != 0 && ch != c) {
                if (!graph.nodes.containsKey(ch)) {
                    graph.nodes.put(ch, new Node(ch));
                }
                graph.nodes.get(ch).children.add(graph.nodes.get(c));
            }
            if (ch == 0) {
                graph.entryPointsOfConnectedComponents.add(graph.nodes.get(c));
            }
            start.characterBefore = c;
            if (start.children[c - 'a'] == null) {
                start.children[c - 'a'] = new TrieNode();
            }
            start = start.children[c - 'a'];
        }
    }
}

class TrieNode {
    char characterBefore = 0;
    TrieNode[] children = new TrieNode[26];
}

class Graph {
    Map<Character, Node> nodes = new HashMap<>();
    ArrayList<Node> entryPointsOfConnectedComponents = new ArrayList<>();

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
        for (Node root: this.entryPointsOfConnectedComponents) {
            topologicalSort(root, visited, alphabet);
        }
        for (int i = alphabet.size() - 1; i >= 0; i--) {
            System.out.print(alphabet.get(i) + " ");
        }
        for (int i = 0; i < 26; i++) {
            if (!visited[i]) {
                System.out.print((char)(i + 'a') + " ");
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
