import java.util.*;

public class Solution {
    // Renamed to pathExistenceQueries to match the driver code
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        // 1. Collect and sort unique values
        TreeSet<Integer> uniqueSet = new TreeSet<>();
        for (int num : nums) {
            uniqueSet.add(num);
        }
        
        int m = uniqueSet.size();
        int[] uniqueVals = new int[m];
        Map<Integer, Integer> valToIdx = new HashMap<>();
        
        int index = 0;
        for (int val : uniqueSet) {
            uniqueVals[index] = val;
            valToIdx.put(val, index);
            index++;
        }
        
        // 2. Compute the immediate greedy right jump for each unique value index
        int[] nextRight = new int[m];
        for (int i = 0; i < m; i++) {
            int target = uniqueVals[i] + maxDiff;
            int idx = Arrays.binarySearch(uniqueVals, target);
            if (idx < 0) {
                idx = -idx - 2; 
            }
            nextRight[i] = idx;
        }
        
        // 3. Build the Binary Lifting Table
        int LOG = 18; // Correctly declared here before loops use it
        int[][] up = new int[m][LOG];
        
        for (int i = 0; i < m; i++) {
            up[i][0] = nextRight[i];
        }
        
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < m; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }
        
        // 4. Process Queries
        int numQueries = queries.length;
        int[] ans = new int[numQueries];
        
        for (int q = 0; q < numQueries; q++) {
            int u = queries[q][0];
            int v = queries[q][1];
            
            if (u == v) {
                ans[q] = 0;
                continue;
            }
            
            int valU = nums[u];
            int valV = nums[v];
            
            if (valU == valV) {
                ans[q] = 1;
                continue;
            }
            
            int idxU = valToIdx.get(Math.min(valU, valV));
            int idxV = valToIdx.get(Math.max(valU, valV));
            
            int maxReachable = idxU;
            for (int j = LOG - 1; j >= 0; j--) {
                maxReachable = up[maxReachable][j];
            }
            if (maxReachable < idxV) {
                ans[q] = -1;
                continue;
            }
            
            int steps = 0;
            int curr = idxU;
            
            for (int j = LOG - 1; j >= 0; j--) {
                if (up[curr][j] < idxV) {
                    curr = up[curr][j];
                    steps += (1 << j);
                }
            }
            
            steps += 1;
            ans[q] = steps;
        }
        
        return ans;
    }
}