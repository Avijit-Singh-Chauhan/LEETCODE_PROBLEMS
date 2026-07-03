import java.util.*;

public class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        
        // Build adjacency list: adj[u] = list of [v, cost]
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        
        int maxCost = 0;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];
            adj[u].add(new int[]{v, cost});
            maxCost = Math.max(maxCost, cost);
        }

        // Binary search for the maximum possible minimum-edge cost
        int low = 0, high = maxCost;
        int answer = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (hasValidPath(n, adj, online, k, mid)) {
                answer = mid; // mid is possible, try to find a larger minimum cost
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }

    private boolean hasValidPath(int n, List<int[]>[] adj, boolean[] online, long k, int minEdgeCost) {
        // minCost[i] stores the minimum total path cost from node 0 to node i
        long[] minCost = new long[n];
        Arrays.fill(minCost, Long.MAX_VALUE);
        minCost[0] = 0;

        // Using Dijkstra's algorithm to find the shortest path under the edge constraint
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        pq.offer(new long[]{0, 0}); // {node, current_total_cost}

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int u = (int) curr[0];
            long currentCost = curr[1];

            if (currentCost > minCost[u]) continue;
            if (u == n - 1) return currentCost <= k;

            for (int[] edge : adj[u]) {
                int v = edge[0];
                int edgeCost = edge[1];

                // Skip if the neighbor is offline or edge cost is below our binary search threshold
                if (!online[v] || edgeCost < minEdgeCost) {
                    continue;
                }

                if (minCost[u] + edgeCost < minCost[v]) {
                    minCost[v] = minCost[u] + edgeCost;
                    pq.offer(new long[]{v, minCost[v]});
                }
            }
        }

        return minCost[n - 1] <= k;
    }
}