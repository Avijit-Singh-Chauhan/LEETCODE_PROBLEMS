import java.util.*;
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) 
    {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;
        k %= total;
        int[][] result = new int[m][n];
        for (int i = 0; i < total; i++) 
        {
            int newIndex = (i + k) % total;
            int oldRow = i / n;
            int oldCol = i % n;
            int newRow = newIndex / n;
            int newCol = newIndex % n;
            result[newRow][newCol] = grid[oldRow][oldCol];
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) 
        {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) 
            {
                row.add(result[i][j]);
            }
            ans.add(row);
        }
        return ans;
    }
}