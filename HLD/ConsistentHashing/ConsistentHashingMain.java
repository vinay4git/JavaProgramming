package HLD.ConsistentHashing;

public class ConsistentHashingMain {
    public static void main(String[] args) {
        ConsistentHashing consistentHashing = new ConsistentHashing(12);

        consistentHashing.addNode("Node1", 3);
        consistentHashing.addNode("Node2", 8);
        consistentHashing.addNode("Node3", 11);

        String key1 = "vianytdfdsjndfe";
        String node1 = consistentHashing.getNodeForKey(key1);
        System.out.println("Node assigned for key " + key1 + " is " + node1);

        consistentHashing.removeNode(8);

        node1 = consistentHashing.getNodeForKey(key1);
        System.out.println("Node assigned for key " + key1 + " is " + node1);
    }
}
