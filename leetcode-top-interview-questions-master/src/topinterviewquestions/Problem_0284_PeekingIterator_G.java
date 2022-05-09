package topinterviewquestions;

import java.util.Iterator;

public class Problem_0284_PeekingIterator_G {
    class PeekingIterator implements Iterator<Integer> {
        Iterator<Integer> iterator;
        Integer storedElem;
        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            if(iterator.hasNext()) {
                storedElem = iterator.next();
            }
            this.iterator = iterator;
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return storedElem;
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            int temp = storedElem;
            if(iterator.hasNext()) {
                storedElem = iterator.next();
            } else {
                storedElem = null;
            }
            return temp;
        }

        @Override
        public boolean hasNext() {
            return storedElem != null;
        }
    }
}
