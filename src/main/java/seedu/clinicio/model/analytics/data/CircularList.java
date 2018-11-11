package seedu.clinicio.model.analytics.data;

//@@author arsalanc-v2

import java.util.ArrayList;

/**
 * A circular list to cycle forwards and backwards through data.
 * Makes use of the existing java ArrayList implementation.
 * Elements cannot be removed.
 */
public class CircularList<T> {

    private ArrayList<T> lst;
    private int index;

    public CircularList() {
        lst = new ArrayList<>();
        index = -1;
    }

    public void add(T val) {
        lst.add(val);
    }

    public T getFirst() {
        if (getSize() == 0) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public T getLast() {
        if (getSize() == 0) {
            return null;
        } else {
            return lst.get(getSize() - 1);
        }
    }

    public T getNext() {
        index += 1;
        if (index > getSize() - 1) {
            index = 0;
        }
        return lst.get(index);
    }

    public T getPrevious() {
        index -= 1;
        if (index < 0) {
            index = getSize() - 1;
        }
        return lst.get(index);
    }

    public void reset() {
        index = -1;
    }

    public int getSize() {
        return lst.size();
    }
}
