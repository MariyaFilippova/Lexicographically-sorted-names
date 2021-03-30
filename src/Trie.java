class Trie {
    private TrieNode root;

    Trie() {
        this.root = new TrieNode();
    }

    boolean search(String key) {
        TrieNode start = root;
        int n = key.length();
        for (int i = 0; i < n; i++) {
            int c = key.charAt(i) - 'a';
            if (start == null) {
                return false;
            }
            if (start.children[c] == null) {
                return false;
            }
            start = start.children[c];
        }
        return !(start == null);
    }

    void insert(String key, Graph graph) {
        TrieNode start = root;
        int n = key.length();
        for (int i = 0; i < n; i++) {
            char c = key.charAt(i);
            char ch = start.characterBefore;
            if (ch != 0 && ch != c) {
                if (!graph.nodes.containsKey(c)) {
                    graph.nodes.put(c, new Node(c));
                }
                if (!graph.nodes.containsKey(ch)) {
                    graph.nodes.put(ch, new Node(ch));
                }
                graph.nodes.get(ch).children.add(graph.nodes.get(c));
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

