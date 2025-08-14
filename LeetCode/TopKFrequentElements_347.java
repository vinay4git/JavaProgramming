package LeetCode;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements_347 {
    public static void main(String[] args) {

    }
}

class Node {
    Integer key;
    Integer frequency;

    public Node(Integer key, Integer value) {
        this.key = key;
        this.frequency = value;
    }
}
class Solution {
    PriorityQueue<Node> maxHeap = new PriorityQueue<>((a,b) -> b.frequency.compareTo(a.frequency));
    Map<Integer, Integer> intFrequency = new HashMap<>();
    public int[] topKFrequent(int[] nums, int k) {
        for(int num : nums) {
            Integer frequency = intFrequency.getOrDefault(num, 0);
            intFrequency.put(num , frequency+1);
        }

        intFrequency.entrySet().stream().forEach(integerIntegerEntry -> {
            Node node = new Node(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
            maxHeap.offer(node);
        });
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll().key;
        }
        return  result;
    }
}