package com.mygdx.orbitals.helpers;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class NodeIterator<T> implements Iterator<T> {
    private Stack<T> elements = new Stack<T>();

    public NodeIterator(Node<T> root) {
        elements.addAll(root.getAll());
    }

    @Override
    public boolean hasNext() {
        return (elements.isEmpty());
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else {
            return elements.pop();
        }
    }
}
