import java.util.TreeMap;

public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums.length < 2 || k == 0 || t < 0)
            return false;

        TreeMap<Integer, Integer> lastIndex = new TreeMap<>();

        int i = 0;
        lastIndex.put(nums[0], 0);

        for (int j = 1; j < nums.length; j++) {
            while (j - i > k - 1) {
                // ensure length is k + 1
                if (lastIndex.get(nums[i]) == i)
                    lastIndex.remove(nums[i]); // if i is the last, remove it
                i++;
            }

            // we'll add nums[j] to the range, see if there's any matches in the existing values
            int e = nums[j];
            if (!lastIndex.subMap(e - t, true, e + t, true).isEmpty())
                return true;

            lastIndex.put(e, j);
        }

        return false;
    }
}