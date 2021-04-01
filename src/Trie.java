import java.util.Map;

/**
 * Prefix tree.
 */
class Trie {

    /**
     * Entry point to trie.
     */
    private final TrieNode root;

    Trie() {
        this.root = new TrieNode();
    }

    /**
     * @param key word.
     * @return if the word is in the trie.
     */
    boolean search(final String key) {
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

    /**
     * Inserts a word into the trie.
     *
     * @param key   word.
     * @param graph stores information
     *              about the order of letters.
     */
    void insert(final String key, final Graph graph) {
        Map<Character, Node> nodes = graph.getNodes();
        TrieNode start = root;
        int n = key.length();
        for (int i = 0; i < n; i++) {
            char c = key.charAt(i);
            char ch = start.characterBefore;
            if (ch != 0 && ch != c) {
                if (!nodes.containsKey(c)) {
                    nodes.put(c, new Node(c));
                }
                if (!nodes.containsKey(ch)) {
                    nodes.put(ch, new Node(ch));
                }
                nodes.get(ch).getChildren().add(nodes.get(c));
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

    /*
     * Implementation notes.
     *
     * If the added word with another word has a common prefix,
     * then the first different letter of the previously added
     * word must go in the generated alphabet before the letter
     * from the later added word.
     * Therefore, for each node in the prefix tree, we will remember
     * the letter that is added to this prefix earlier and add
     * to children of the node that corresponding to this letter
     * the node that corresponding to the next letter.
     */

    /**
     * The letter that is added to this prefix earlier.
     */
    char characterBefore = 0;

    /**
     * Possible next letters for the prefix.
     */
    final TrieNode[] children = new TrieNode[Constants.ALPHABET_SIZE];
}

