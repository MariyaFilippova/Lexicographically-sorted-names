import java.util.*;

class Tree {
    String[] names;
    Node root;

    Tree(String[] n) {
        names = n;
    }

    void addChildren(ArrayList<Character> list, char current) {
        for (char ch : list) {
            if (current == ch) {
                continue;
            }
            if (Node.map.containsKey(current) && Node.map.get(current).children[ch - 'a'] != null) {
                System.out.println("Imposible");
                return;
            }
            if (Node.map.containsKey(current)) {
                Node.map.get(ch).children[current - 'a'] = Node.map.get(current);
            }
            else {
                Node.map.get(ch).children[current - 'a'] = new Node(current);
            }
        }
        list.add(current);
    }

    void build() {
        if (this.root == null) {
            root = new Node(names[0].charAt(0));
        }
        ArrayList<Character> list = new ArrayList<>();
        list.add(root.value);
        for (int i = 0; i < names.length; i++) {
            char current = names[i].charAt(0);
            addChildren(list, current);
        }
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
