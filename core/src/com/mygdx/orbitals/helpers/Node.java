package com.mygdx.orbitals.helpers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Node <T> {
    Class type;

    ArrayList<T> objects;
    Hashtable<String, Node<T>> children;

    Node(Class c) {
        type = c;
        objects = new ArrayList<T>();
        children = new Hashtable<String, Node<T>>();
    }

    public ArrayList<T> getAll() {
        ArrayList<T> list = new ArrayList<T>();

        list.addAll(objects);

        Enumeration<Node<T>> elements = children.elements();
        while (elements.hasMoreElements()) {
            list.addAll(elements.nextElement().getAll());
        }
        return list;
    }
}
