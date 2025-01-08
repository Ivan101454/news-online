package ru.clevertec.newsonline.cache;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LFUCache<K, V> implements Cache<K, V> {
    private final int maxSize;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequencies;
    private final PriorityQueue<K> minHeap;

    public LFUCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new HashMap<>();
        this.frequencies = new HashMap<>();
        this.minHeap = new PriorityQueue<>((a, b) -> frequencies.get(a) - frequencies.get(b));
    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        frequencies.put(key, frequencies.get(key) + 1);
        minHeap.remove(key);
        minHeap.offer(key);
        return cache.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (cache.size() >= maxSize) {
            K leastFrequentKey = minHeap.poll();
            cache.remove(leastFrequentKey);
            frequencies.remove(leastFrequentKey);
        }
        V put = cache.put(key, value);
        frequencies.put(key, 1);
        minHeap.offer(key);
        return put;
    }

    @Override
    public V remove(K key) {
        V remove = cache.remove(key);
        frequencies.remove(key);
        minHeap.remove(key);
        return remove;
    }
}

