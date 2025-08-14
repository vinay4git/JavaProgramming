package interview2025;

class MinCostToReachTopOfStaircase {
    public static void main(String[] args) {
/*
Given an Steps of specific cost. We can take either 1 or 2 steps at a time.
Find the minimum possible cost to reach top of staircase from the bottom.

        [1,100,1,1,1,100,1,1,100,1]
        step 1  -> 1
        step 3 --> 2
        step 5 --> 3
        step 7 --> 4
        step 8 --> 5
        step 10 --> 6
* */

        int[] cost = {1,100,1,1,1,100,1,1,100,1};

        if (cost.length == 1) {
            System.out.println(cost[0]);
        } else if ( cost.length == 2) {
            System.out.println(Math.min(cost[0], cost[1]));
        } else {
            for (int i = 2; i < cost.length; i++) {
                cost[i] = Math.min(cost[i-1], cost[i-2]) + cost[i];
            }
            //
            System.out.println(Math.min(cost[cost.length-1], cost[cost.length-2]));
        }
        
    }
    
}