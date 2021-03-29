import java.util.*;

class Tree {
    private String[] names;
    private Node root;

    Tree(String[] n) {
        names = n;
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
        topologicalSort(root, visited, alphabet);
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

    void build() {
        char prev = 0;
        if (this.root == null) {
            root = new Node(names[0].charAt(0));
            prev = root.value;
        }
        for (int i = 0; i < names.length; i++) {
            char current = names[i].charAt(0);
            if (prev == current) {
                continue;
            }
            if (Node.nodes.containsKey(current)) {
                Node.nodes.get(prev).children.add(Node.nodes.get(current));
            }
            else {
                Node.nodes.get(prev).children.add(new Node(current));
            }
        }
    }
}


class Node {
    static Map<Character, Node> nodes = new HashMap<>();
    Character value;
    ArrayList<Node> children;

    Node(Character character) {
        value = character;
        nodes.put(character, this);
        children = new ArrayList<>();
    }
}
