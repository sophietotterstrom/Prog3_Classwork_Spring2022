import java.util.*;
import java.util.function.Consumer;

public class ArrayNode extends Node implements Iterable<Node> {

    private ArrayList<Node> nodesArray;

    private int currentSize = 0;

    public ArrayNode() {
        this.nodesArray = new ArrayList<>();
    }

    public void add(Node node) {

        this.nodesArray.add(node);

        this.currentSize++;
    }

    public int size() {
        return this.currentSize;
    }


    @Override
    public Iterator<Node> iterator() {
        Iterator<Node> it = new Iterator<Node>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < currentSize && nodesArray.get(currentIndex) != null;
            }

            @Override
            public Node next() {
                return nodesArray.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public void forEach(Consumer<? super Node> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Node> spliterator() {
        return Iterable.super.spliterator();
    }
}