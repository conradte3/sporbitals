package com.mygdx.orbitals.helpers;

/**
 * Created by Conrad on 10/30/2015.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A list of objects which all inherit from one type
 * Different types of children are stored separately
 */
public class TypedList {
    /*private List<List<GameObject>> list;

    public TypedList() {
        list = new ArrayList<List<GameObject>>();
        for(GameObject.Type type : GameObject.Type.values()) {
            list.add(new ArrayList<GameObject>());
        }
    }

    public void add(GameObject go) {
        list.get(go.getType()).add(go);
    }

    public void remove(GameObject go) {
        list.get(go.getType()).remove(go);
    }

    public List<GameObject> get(GameObject.Type t) {
        return list.get(t.value());
    }

    @Override
    public Iterator iterator() {
        return new TypedListIterator();
    }

    private class TypedListIterator implements Iterator<GameObject> {
        int currentList = 0;
        int currentElement = 0;

        @Override
        public boolean hasNext() {
            int i = currentList;
            if (currentElement >= list.get(currentList).size()) { //past end of current list
                if (currentList >= list.size() - 1) { //at end of outer list
                    return false; //there is no next
                } else { //there are more lists
                    for (i++; i < list.size(); i++) { //look at all remaining lists
                        if (!list.get(i).isEmpty()) { //found non-empty
                            return true; //there is a next
                        }
                    }
                }
            } else { //there are more elements in current list
                return true; //there is a next
            }
            return false; //no next found
        }

        @Override
        public GameObject next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                if (currentElement >= list.get(currentList).size()) { //Past end of current list
                    //Look for the next non-empty list and go to element 0
                    for (currentElement = 0, currentList++; list.get(currentList).isEmpty(); currentList++);
                }
                return list.get(currentList).get(currentElement++);
            }
        }
    }*/
}