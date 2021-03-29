import java.util.*;

class Tree {
    private String[] names;
    private Node root;

    Tree(String[] n) {
        names = n;
    }

    void printAlphabet() {
        ArrayList<Character> alphabet = new ArrayList<>();
        boolean[] visited =  new boolean[26];
        this.topologicalSort(this.root, visited, alphabet);
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
        for (int i = 0; i < root.children.length; i++) {
            topologicalSort(root.children[i], visited, alphabet);
        }
        alphabet.add(root.value);
    }

    private boolean addChildren(ArrayList<Character> list, char current) {
        for (char ch : list) {
            if (current == ch) {
                continue;
            }
            if (Node.map.containsKey(current) && Node.map.get(current).children[ch - 'a'] != null) {
                return false;
            }
            if (Node.map.containsKey(current)) {
                Node.map.get(ch).children[current - 'a'] = Node.map.get(current);
            }
            else {
                Node.map.get(ch).children[current - 'a'] = new Node(current);
            }
        }
        list.add(current);
        return true;
    }

    boolean build() {
        if (this.root == null) {
            root = new Node(names[0].charAt(0));
        }
        ArrayList<Character> list = new ArrayList<>();
        list.add(root.value);
        for (int i = 0; i < names.length; i++) {
            char current = names[i].charAt(0);
            if (!addChildren(list, current)) {
                System.out.println("Imposible");
                return false;
            }
        }
        return true;
    }

}


class Node {
    static Map<Character, Node> map = new HashMap<>();
    Character value;
    Node[] children;

    Node(Character character) {
        value = character;
        map.put(character, this);
        children = new Node[26];
    }
}
