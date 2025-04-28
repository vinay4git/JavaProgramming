package HLD.ConsistentHashing;

import java.util.*;

public class ConsistentHashing {

    int circularQueueSize;
    Map<Integer, String> q;

    public ConsistentHashing(int circularQueueSize) {
        this.circularQueueSize = circularQueueSize;

        q = new HashMap<>();
        for (int i = 0; i < circularQueueSize; i++) {
            q.put(i,null);
        }
    }

    public void addNode(String nodeName, int index) {
        if (index >= circularQueueSize && index < 0){
            throw new RuntimeException("Invalid index to add the node");
        }

            q.put(index, nodeName);
    }

    public void removeNode(int index) {
        if (index >= circularQueueSize && index < 0){
            throw new RuntimeException("Invalid index to remove the node");
        }
        q.put(index,null);
    }

    public String getNodeForKey(String key) {
        int hashCode = key.hashCode();

        int queuePosition = Math.abs(hashCode) % circularQueueSize;

        return getServingNodeForPosition(queuePosition);
    }

    private String getServingNodeForPosition(int index){
        int i = index;

        while (q.get(i) == null) {
            if (i == circularQueueSize)
                i = 0;

            i++;
        }

        return q.get(i);
    }
}
