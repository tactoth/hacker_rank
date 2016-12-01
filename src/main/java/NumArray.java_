public class NumArray {

    private final int[] nums;
    private final int[] tree;

    // for full binary tree
    static int left(int index) {
        return 2 * index + 1;
    }

    static int right(int index) {
        return 2 * index + 2;
    }

    // for split index for range, left will carry more
    static int mid(int start, int end) {
        return (start + end + 1) / 2;
    }

    public NumArray(int[] nums) {
        this.nums = nums;

        // build segment tree
        int width = 1, start = 0, end = nums.length, totalTreeNodes = 0;
        while (start < end) {
            totalTreeNodes += width;
            width *= 2;

            int mid = mid(start, end);

            // might be no update at all
            if (mid == end)
                break;

            end = mid;
        }
        tree = new int[totalTreeNodes];
        build(0, 0, nums.length);
    }

    int sum(int root, int treeStart, int treeEnd, int qstart, int qend) {
        // it's required qstart-qend is a subset of ts-te: TODO: validate and exp
        if (qstart == treeStart && qend == treeEnd) return tree[root];
        int treeMid = mid(treeStart, treeEnd);

        if (qend <= treeMid) return sum(left(root), treeStart, treeMid, qstart, qend);
        else if (qstart >= treeMid) return sum(right(root), treeMid, treeEnd, qstart, qend);
        else {
            return sum(left(root), treeStart, treeMid, qstart, treeMid) + sum(right(root), treeMid, treeEnd, treeMid, qend);
        }
    }

    void build(int root, int treeStart, int treeEnd) {
        if (treeStart == treeEnd) return; // empty
        if (treeStart + 1 == treeEnd) tree[root] = nums[treeStart];
        else {
            int treeMid = mid(treeStart, treeEnd);
            build(left(root), treeStart, treeMid);
            build(right(root), treeMid, treeEnd);
            tree[root] = tree[left(root)] + tree[right(root)];
        }
    }

    void update(int root, int treeStart, int treeEnd, int index, int delta) {
        tree[root] += delta;
        int treeMid = mid(treeStart, treeEnd);
        if (index < treeMid) {
            if (treeMid != treeEnd)
                update(left(root), treeStart, treeMid, index, delta);
        } else { // >=
            update(right(root), treeMid, treeEnd, index, delta);
        }
    }

    void dump(int root, int treeStart, int treeEnd) {
        if (treeEnd <= treeStart) return;

        System.out.println(String.format("[%d,%d) => %d", treeStart, treeEnd, tree[root]));
        int mid = mid(treeStart, treeEnd);
        if (mid != treeEnd) dump(left(root), treeStart, mid);
        dump(right(root), mid, treeEnd);
    }

    void dump(String messge) {
        System.out.println("Message: " + messge);
        dump(0, 0, nums.length);
    }


    void update(int i, int val) {
        int delta = val - nums[i];
        nums[i] = val;
        update(0, 0, nums.length, i, delta);
    }

    public int sumRange(int i, int j) {
        return sum(0, 0, nums.length, i, j + 1);
    }

    public static void main(String[] args) {
//        NumArray numArray = new NumArray(new int[]{1, 3, 5});
//        TestUtil.test(numArray.sumRange(0, 2), 9);
//        TestUtil.test(numArray.sumRange(0, 0), 1);
//        TestUtil.test(numArray.sumRange(0, 1), 4);
//        TestUtil.test(numArray.sumRange(1, 1), 3);
//        TestUtil.test(numArray.sumRange(1, 2), 8);
//
//
//        numArray.update(1, 2);
//        TestUtil.test(numArray.sumRange(0, 2), 8);
//        TestUtil.test(numArray.sumRange(0, 0), 1);
//        TestUtil.test(numArray.sumRange(0, 1), 3);
//        TestUtil.test(numArray.sumRange(1, 1), 2);
//        TestUtil.test(numArray.sumRange(1, 2), 7);

        NumArray numArray = new NumArray(new int[]{7, 2, 7, 2, 0});
        numArray.dump("create");
        numArray.update(4, 6);
        numArray.dump("4 to 6");

        numArray.update(0, 2);
        numArray.dump("0 to 2");

        numArray.update(0, 9);
        numArray.dump("0 to 2 to 9");

        numArray.update(3, 8);
        numArray.update(4, 1);
        numArray.update(0, 4);
        numArray.dump("Lots of them");
    }
}


// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.update(1, 10);
// numArray.sumRange(1, 2);