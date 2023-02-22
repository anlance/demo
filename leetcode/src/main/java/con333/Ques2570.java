package con333;

import java.util.Arrays;

/**
 * 合并两个二维数组 - 求和法
 *
 * @author lan
 * @version 1.0
 * @date 2023/2/22 22:56
 */
public class Ques2570 {

    class Solution {
        public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
            int i = 0;
            int j = 0;
            int k = 0;
            int[][] mergeArrays = new int[nums1.length + nums2.length][2];
            while (i < nums1.length || j < nums2.length) {
                if (i == nums1.length) {
                    mergeArrays[k][0] = nums2[j][0];
                    mergeArrays[k][1] = nums2[j][1];
                    j++;
                    k++;
                } else if (j == nums2.length) {
                    mergeArrays[k][0] = nums1[i][0];
                    mergeArrays[k][1] = nums1[i][1];
                    i++;
                    k++;
                } else {
                    if (nums1[i][0] == nums2[j][0]) {
                        mergeArrays[k][0] = nums1[i][0];
                        mergeArrays[k][1] = nums1[i][1] + nums2[j][1];
                        i++;
                        j++;
                        k++;
                    } else if (nums1[i][0] > nums2[j][0]) {
                        mergeArrays[k][0] = nums2[j][0];
                        mergeArrays[k][1] = nums2[j][1];
                        j++;
                        k++;
                    } else {
                        mergeArrays[k][0] = nums1[i][0];
                        mergeArrays[k][1] = nums1[i][1];
                        i++;
                        k++;
                    }
                }

            }
            return Arrays.copyOf(mergeArrays, k);
        }
    }

    public static void main(String[] args) {
        Solution a = new Ques2570().new Solution();
        int[][] nums1 = new int[][]{{2, 4}, {4, 6}, {5, 5}};
        int[][] nums2 = new int[][]{{1, 3}, {4, 3}};
        int[][] ints = a.mergeArrays(nums1, nums2);
        for (int[] s : ints) {
            System.out.println(s[0] + ":" + s[1]);
        }
        System.out.println(ints);
    }
}
