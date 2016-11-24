import java.util.*;

public class Solution {
    private static final String VALID_CHARS = "ACGT";

    public int minMutation(String start, String end, String[] bank) {
        Set<String> set = new HashSet<>(Arrays.asList(bank));
        set.add(start);
        // set.add(end);

        Map<String, Integer> dist = new HashMap<>();
        dist.put(start, 0);

        List<String> q = new ArrayList<>(set);
        while (!q.isEmpty()) {
            String nearest = null;
            int nearestDist = Integer.MAX_VALUE;
            for (String s : q) {
                Integer d = dist.get(s);
                if (d != null && d < nearestDist) {
                    nearest = s;
                    nearestDist = d;
                }
            }

            // remove it
            if (nearest == null) {
                // goes to a dead end
                break;
            }

            q.remove(nearest);

            // check all neighbours
            StringBuilder sb = new StringBuilder(nearest);
            for (int i = 0; i < sb.length(); i++) {
                char oldChar = sb.charAt(i);
                for (int j = 0; j < VALID_CHARS.length(); j++) {
                    sb.setCharAt(i, VALID_CHARS.charAt(j));
                    String neighbour = sb.toString();
                    if (set.contains(neighbour)) {
                        Integer oldDist = dist.get(neighbour);
                        if (oldDist == null || oldDist > nearestDist + 1) {
                            dist.put(neighbour, nearestDist + 1);
                        }
                    }
                }
                sb.setCharAt(i, oldChar);
            }
        }

        Integer result = dist.get(end);
        return result == null ? -1 : result;

    }

    public static void main(String[] args) {
        System.out.println(new Solution().minMutation("AACCGGTT", "AACCGGTA", new String[]{}));
        System.out.println(new Solution().minMutation("AACCGGTT", "AACCGGTA", new String[]{"AACCGGTA"}));
        System.out.println(new Solution().minMutation("AACCGGTT", "AAACGGTA", new String[]{"AACCGGTA", "AACCGCTA", "AAACGGTA"}));
        System.out.println(new Solution().minMutation("AAAAACCC", "AACCCCCC", new String[]{"AAAACCCC", "AAACCCCC", "AACCCCCC"}));
    }
}