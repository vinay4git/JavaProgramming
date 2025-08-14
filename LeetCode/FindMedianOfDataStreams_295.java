package LeetCode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class FindMedianOfDataStreams_295 {

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();

        medianFinder.addNum(1);
        medianFinder.addNum(2);
        medianFinder.addNum(3);
        medianFinder.addNum(4);

        System.out.println(medianFinder.findMedian());
    }
}

class MedianFinder {

    PriorityQueue<Integer> largeNumMinHeap;
    PriorityQueue<Integer> smallNumMaxHeap;

    public MedianFinder() {
        largeNumMinHeap = new PriorityQueue<>();
        smallNumMaxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }

    public void addNum(int num) {
        largeNumMinHeap.offer(num);
        smallNumMaxHeap.offer(largeNumMinHeap.poll());
        if (smallNumMaxHeap.size() > largeNumMinHeap.size()) {
            largeNumMinHeap.offer(smallNumMaxHeap.poll());
        }
    }

    public double findMedian() {
        if (smallNumMaxHeap.size() == largeNumMinHeap.size()) {
            return ( largeNumMinHeap.peek() / 2.0 + smallNumMaxHeap.peek() / 2.0) ;
        } else {
            return largeNumMinHeap.peek();
        }
    }
};