package interview2025;

import java.util.ArrayDeque;

class MaxAmongMinimumOfAFixedWindow {
    /*
    find the minimum of every window of size 3 (k) in an array. Among those minimum, later find the maximum value
    Example: [10, 20, 30, 50, 10, 70, 30]
    consider all windows: [10, 20, 30], [20, 30, 50], [30, 50, 10], [50, 10, 70], [10, 70, 30]. The minimum values are 10, 20, 10, 10, 10 — the maximum of these is 20.
    * */
    public static void main(String[] args) {
        int[] nums = {10, 5, 30, 50, 10, 70, 30};
        int k = 7;
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        int left = 0, right = 0;
        int currentMax = Integer.MIN_VALUE;

        while (true) {

            if( right < k) {
                while(!deque.isEmpty() && nums[deque.getLast()] > nums[right])  deque.pollLast();
                deque.offerLast(right);
                right++;
                continue;
            }

            int windowMin =  nums[deque.getFirst()];
            currentMax = Math.max(currentMax, windowMin);

            if (left == deque.getFirst()) {
                deque.pollFirst();
            }

            if ( right == nums.length)
                break;

            while(!deque.isEmpty() && nums[deque.getLast()] > nums[right])  deque.pollLast();
            deque.offerLast(right);

            left++;
            right++;
        }
        System.out.println(currentMax);
    }
}