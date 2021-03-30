import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    static class Node {
        Character value;
        ArrayList<Node> children;

        Node(Character character) {
            value = character;
            children = new ArrayList<>();
        }
    }
}
