package box.ascension.app.nb2.game.hq;

import java.util.ArrayList;
import java.util.List;

public class ProgTree<T> {
    
    public List<ProgTree<T>> children = new ArrayList<>();
    public boolean isFinal = true;
    public boolean unlocked = false;
    public T unlocks;

    public ProgTree(T item, ArrayList<ProgTree<T>> children) {
        this.children = children;
        this.isFinal = false;
        this.unlocks = item;
    }

    public ProgTree(T item) {
        this.unlocks = item;
    }
    
    public ProgTree() {
        this.unlocks = null;
    }

    public static <T> ProgTree<T> of(T item) {
        return new ProgTree<>(item);
    }

    @SuppressWarnings("unchecked")
    public ProgTree<T> and(ProgTree<T>... trees) {
        children.addAll(List.of(trees));
        return this;
    }

    public List<ProgTree<T>> unlock() {
        this.unlocked = true;
        return this.children;
    }

    public ProgTree<T> get(int[] indices) {
        ProgTree<T> current = this;
        for (int i = 0; i < indices.length; i++) {
            current = current.children.get(indices[i]);
        }
        return current;
    }

    public boolean isUnlocked(int[] indices) {
        ProgTree<T> current = this;
        for (int i = 0; i < indices.length; i++) {
            if (!current.unlocked) {
                return false;
            }
            current = current.children.get(indices[i]);
        }
        return true;
    }

}
