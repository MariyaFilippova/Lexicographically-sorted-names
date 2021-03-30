import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class TrieTest {
    @Test
    public void checkParsingOfInput() {
        try (FileInputStream readerNames = new FileInputStream("/Users/mariafilippova/Names-in-article/test/resources/names.txt");
             FileInputStream readerResult = new FileInputStream("/Users/mariafilippova/Names-in-article/test/resources/result.txt")) {
            String[] names = Parser.parseInput(readerNames);
            Scanner scanner = new Scanner(readerResult);
            int n = scanner.nextInt();
            String[] result = new String[n];
            for (int i = 0; i < n; i++) {
                result[i] = scanner.next().toLowerCase();
            }
            Assertions.assertArrayEquals(names, result);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void checkCorrectInsertionInTrie() {
        try (FileInputStream readerNames = new FileInputStream("/Users/mariafilippova/Names-in-article/test/resources/names.txt");
             FileInputStream readerResult = new FileInputStream("/Users/mariafilippova/Names-in-article/test/resources/result.txt")) {
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
                Assertions.assertTrue(trie.search(result[i]));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }



}