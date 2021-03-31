import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TrieTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void checkParsingOfInput() {
        try (FileInputStream readerNames = new FileInputStream("./test/resources/names.txt");
             FileInputStream readerResult = new FileInputStream("./test/resources/result.txt")) {
            String[] names = Parser.parseInput(readerNames);
            Scanner scanner = new Scanner(readerResult);
            int n = scanner.nextInt();
            String[] result = new String[n];
            for (int i = 0; i < n; i++) {
                result[i] = scanner.next().toLowerCase();
            }
            assertArrayEquals(names, result);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void checkCorrectInsertionInTrie() {
        try (FileInputStream readerNames = new FileInputStream("./test/resources/names.txt");
             FileInputStream readerResult = new FileInputStream("./test/resources/result.txt")) {
            Trie trie = new Trie();
            Graph graph = new Graph();
            String[] names = Parser.parseInput(readerNames);
            Scanner scanner = new Scanner(readerResult);
            int n = scanner.nextInt();
            String[] result = new String[n];
            for (int i = 0; i < n; i++) {
                result[i] = scanner.next().toLowerCase();
            }
            for (int i = 0; i < names.length; i++) {
                trie.insert(names[i], graph);
            }
            for (int i = 0; i < names.length; i++) {
                assertTrue(trie.search(result[i]));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void namesWithDifferentFirstLetters() {
        try (FileInputStream readerNames = new FileInputStream("./test/resources/differentFirstLetters.txt")) {
            Scanner scanner = new Scanner(readerNames);
            int n = scanner.nextInt();
            String[] result = new String[n];
            StringBuilder firstLetters = new StringBuilder();
            for (int i = 0; i < n; i++) {
                result[i] = scanner.next().toLowerCase();
                firstLetters.append(result[i].charAt(0));
                firstLetters.append(' ');
            }
            Graph graph = new Graph();
            Map<Character, Node> nodes = graph.getNodes();
            Trie trie = new Trie();
            for (int i = 0; i < n; i++) {
                trie.insert(result[i], graph);
            }
            assertEquals(nodes.size(), n);
            assertEquals(graph.connectedComponents(), 1);
            graph.printAlphabet();
            assertTrue(testOut.toString().contains(firstLetters.toString()));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void negativeResult() {
        try (FileInputStream readerNames = new FileInputStream("./test/resources/names.txt")) {
            Trie trie = new Trie();
            Graph graph = new Graph();
            String[] names = Parser.parseInput(readerNames);
            for (int i = 0; i < names.length; i++) {
                trie.insert(names[i], graph);
            }
            graph.printAlphabet();
            assertEquals(testOut.toString(), "Impossible\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void namesWithSamePrefix() {
        try (FileInputStream readerNames = new FileInputStream("./test/resources/namesWithSamePrefix.txt")) {
            Trie trie = new Trie();
            Graph graph = new Graph();
            Map<Character, Node> nodes = graph.getNodes();
            String[] names = Parser.parseInput(readerNames);
            for (int i = 0; i < names.length; i++) {
                trie.insert(names[i], graph);
            }
            assertEquals(nodes.size(), 11);
            assertEquals(graph.connectedComponents(), 3);
            graph.printAlphabet();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}