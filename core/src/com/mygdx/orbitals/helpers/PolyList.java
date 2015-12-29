package com.mygdx.orbitals.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Created by Conrad on 11/22/2015.
 */
public class PolyList<T> implements Iterable<T> {
    private Node<T> root;

    public PolyList(Class<T> tClass) {
        root = new Node<T>(tClass);
    }

    public void add(T object) {
        getNode(object.getClass(), true).objects.add(object);
    }

    public void remove(T object) {
        Node<T> node = getNode((Class<? extends T>) object.getClass());
        if (node != null) {
            node.objects.remove(object);
        }
    }

    public T get(T object) {
        Node<T> node = getNode((Class<? extends T>) object.getClass());
        if (node != null) {
            return node.objects.get(node.objects.indexOf(object));
        }
        return null;
    }

    public ArrayList<T> elements() {
        return elements(root.type);
    }

    public ArrayList<T> elements(Class<? extends T> c) {
        if (getNode(c) == null) {
            return new ArrayList<T>();
        } else {
            return getNode(c).getAll();
        }
    }

    public Node<T> getNode(Class<? extends T> c) {
        return getNode(c, false);
    }

    private Node<T> getNode(Class c, boolean insertMissing) {
        Class current;
        Stack<Class<? extends T>> parents = new Stack<Class<? extends T>>();

        current = c;

        while (current != root.type) {
            parents.push(current);
            current = current.getSuperclass();
        }
        return getNode(root, parents, insertMissing);
    }

    private Node<T> getNode(Node<T> tree, Stack<Class<? extends T>> parents, boolean insertMissing) {
        if (parents.isEmpty()) { //Got down to the node with type tClass
            return tree;
        } else { //Need to go further down the tree
            String name = parents.peek().getName();
            if (tree.children.containsKey(name)) {
                parents.pop();
                return getNode(tree.children.get(name), parents, insertMissing);
            } else { //There is no node with this type
                if (insertMissing) {
                    Node<T> newNode = new Node<T>(parents.peek());
                    tree.children.put(name, newNode);
                    parents.pop();
                    return getNode(newNode, parents, insertMissing);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public Iterator iterator() {
        return new PolyIterator<T>(root);
    }

    class PolyIterator<T> implements Iterator<T> {
        private Stack<T> elements = new Stack<T>();

        public PolyIterator(Node<T> tNode) {
            elements.addAll(tNode.getAll());
        }

        @Override
        public boolean hasNext() {
            return (!elements.isEmpty());
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
}

