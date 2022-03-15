package algo.demo.map;

import java.util.Set;

public interface Map<K,V> {

    V put(K key,V value);

    V get(K key);

    int size();

    boolean isEmpty();

    Set<K> keySet();

    Set<Entry<K,V>> entrySet();

    interface Entry<K,V>{
        K getKey();
        V getValue();
    }
}
