class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1;
        int prev = -1;
        int minDist = Integer.MAX_VALUE;
        int maxDist = -1;
        int index = 1;

        ListNode prevNode = head;
        ListNode curr = head.next;

        while (curr != null && curr.next != null) {
            if ((curr.val > prevNode.val && curr.val > curr.next.val) ||
                (curr.val < prevNode.val && curr.val < curr.next.val)) {

                if (first == -1) {
                    first = index;
                }

                if (prev != -1) {
                    minDist = Math.min(minDist, index - prev);
                    maxDist = index - first;
                }

                prev = index;
            }

            prevNode = curr;
            curr = curr.next;
            index++;
        }

        if (maxDist == -1) {
            return new int[]{-1, -1};
        }

        return new int[]{minDist, maxDist};
    }
}