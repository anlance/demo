package con333;

/**
 * 将整数减少到零需要的最少操作数
 *
 * @author lan
 * @version 1.0
 * @date 2023/2/22 22:56
 */
public class Ques2571 {

    class Solution {
        int minOperations(int n) {
            int[] num = new int[18];
            int x = 1;
            for (int i = 0; i < 18; i++) {
                num[i] = x;
                System.out.println(x);
                x *= 2;
            }
            int step = 0;
            while (n != 0) {
                for (int i = 0; i < 18; i++) {
                    if (n >= num[i] && n <= num[i + 1]) {
                        n = Math.min(n - num[i], num[i + 1] - n);
                        step+=1;
                        break;
                    }
                }
            }
            return step;
        }
    }

    public static void main(String[] args) {
        Solution a = new Ques2571().new Solution();
        System.out.println(a.minOperations(100000));
    }
}
