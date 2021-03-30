import java.io.InputStream;
import java.util.Scanner;

class Parser {
    static String[] parseInput(final InputStream in) {
        Scanner scanner = new Scanner(in);
        int n = scanner.nextInt();
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            StringBuilder data = new StringBuilder(scanner.next());
            int index = data.indexOf("%");
            int place = Integer.parseInt(data.substring(index + 1, data.length()));
            String name = data.substring(0, index).toLowerCase();
            names[place] = name;
        }
        return names;
    }

    public static void main(String[] args) {
        String[] names = Parser.parseInput(System.in);
        Graph graph = new Graph();
        Trie trie = new Trie();
        for (int i = 0; i < names.length; i++) {
            trie.insert(names[i], graph);
        }
        graph.printAlphabet();
    }
}
