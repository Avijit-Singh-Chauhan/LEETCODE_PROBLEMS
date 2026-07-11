import java.util.*;

public class Solution 
{
    public int countCompleteComponents(int n, int[][] edges) 
    {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) 
        {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) 
        {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        int completeComponentsCount = 0;
        for (int i = 0; i < n; i++) 
        {
            if (!visited[i]) 
            {
                int[] componentInfo = new int[2]; 
                bfs(i, adj, visited, componentInfo);
                int vertexCount = componentInfo[0];
                int totalDegree = componentInfo[1];
                int edgeCount = totalDegree / 2;
                if (edgeCount == (vertexCount * (vertexCount - 1)) / 2) 
                {
                    completeComponentsCount++;
                }
            }
        }
        return completeComponentsCount;
    }
    private void bfs(int start, List<List<Integer>> adj, boolean[] visited, int[] componentInfo) 
    {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        while (!queue.isEmpty()) 
        {
            int curr = queue.poll();
            componentInfo[0]++;
            componentInfo[1] += adj.get(curr).size(); 
            for (int neighbor : adj.get(curr)) 
            {
                if (!visited[neighbor]) 
                {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }
}