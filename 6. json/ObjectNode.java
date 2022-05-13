import java.util.*;
import java.util.function.Consumer;

public class ObjectNode extends Node implements Iterable<String>  {

    private int currentSize = 0;

    private HashMap<String, Node> nodeMap;
    private ArrayList<String> keyArray = new ArrayList<>();

    public ObjectNode() {
        this.nodeMap = new HashMap<>();
    }

    public Node get(String key) {

        return this.nodeMap.get(key);
    }

    public void set(String key, Node node) {
        this.nodeMap.put(key, node);

        keyArray.add(key);
        Collections.sort(keyArray);
        currentSize++;
    }

    public int size() {
        return this.currentSize;
    }

    @Override
    public Iterator<String> iterator() {
        Iterator<String> it = new Iterator<String>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {

                return currentIndex < currentSize && keyArray.get(currentIndex) != null;
            }

            @Override
            public String next() {

                return keyArray.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return Iterable.super.spliterator();
    }
}