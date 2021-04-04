package com.names;

import java.util.ArrayList;

/**
 * An object that represents a single letter.
 */
public class Node {

    /**
     * Letter.
     */
    private final char value;

    public char getValue() {
        return value;
    }

    /**
     * Nodes corresponding to the letters
     * that should be after current
     * in the generated alphabet.
     */
    private final ArrayList<Node> children;

    public ArrayList<Node> getChildren() {
        return children;
    }

    Node(char character) {
        value = character;
        children = new ArrayList<>();
    }
}