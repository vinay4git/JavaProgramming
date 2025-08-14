package LeetCode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MergeIntervals_56 {
    public static void main(String[] args) {
        SolutionMI solutionMI = new SolutionMI();
        solutionMI.merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});
    }
}

class SolutionMI {
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        LinkedList<int[]> merged = new LinkedList<>();

        for (int[] interval : intervals) {
            if (!merged.isEmpty() && merged.getLast()[1] >= interval[0]) {
                merged.getLast()[1] = Math.max(interval[1], merged.getLast()[1]);
            } else {
                merged.add(interval);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
}