package offer;

/**
 * Created by huali on 2018/2/5.
 */
//从1到n的整数中1出现的次数。
public class NumberOf1Between1AndN_Solution {

    ///解法1：经典解法，容易想到，时间复杂度O(n*logn)
    public int NumberOf1Between1AndN_1(int n) {
        if (n<=0)
            return 0;

        int number=0;
        for (int i=1;i<=n;i++)
            number+=countNumber1(i);

        return number;
    }

    public int countNumber1(int m){
        int number =0;
        while (m!=0){
            if (m%10 ==1)
                number++;

            m=m/10;
        }

        return number;
    }


    ///解法2：O(logn)的解法，从数字规律着手
    //去掉最高位做递归，递归的次数和位数相同
    //递归的次数和位数相同，一个数字有logn为，
    // 因此时间复杂度为O(logn)

    /**
     * 1. 取第 i 位左边（高位）的数字，乘以 10^(i−1)，得到基础值 a。
     * 2. 取第 i 位数字，计算修正值：
     *      1.如果大于 X，则结果为 a+10^(i−1)。
     *      2.如果小于 X，则结果为 a。
     *      3.如果等 X，则取第 i 位右边（低位）数字，设为 b，最后结果为 a+b+1。
     */
    /**
    //算法的过程：是从最低位开始，比如21345
    //去掉最后一位5，变为2134，最后一位为1时，总共有2134个1，再加上5>1，数字1，也就是2135，
    // 然后再去掉一位4，变为213，总共有213x10个1，4>1，故有4x10个1，2130+10个1
    //在去掉一位，变为21,21x100个1，再加上数3>1，位置上有100个1，共有2100+100个1
    //最后在去掉一位为2，，2x1000个1,再加上数1=1，位置上有345+1个1，共2000+345+1
    // 看是否为1，大于1则有1000个，等于1看一后面数是多少+1就有多少个。小于1没有
    //最后一个2>1,公有10000个1。加起来公有：2135+2130+10+2100+100+2000+345+1+10000
     */
    public int NumberOf1Between1AndN_2(int n) {
        if (n <= 0)
            return 0;
        int i = 1;
        int high = n, cur, low, result = 0;
        int base = 0, modify = 0;
        while (high != 0) {
            //i代表目前第几位。
            high = n / (int) Math.pow(10, i);
            cur = (n % (int) Math.pow(10, i)) / (int) Math.pow(10, i - 1);
            low = n % (int) Math.pow(10, i - 1);

            base = high * (int) Math.pow(10, i - 1);
            if (cur == 0) {
                modify = 0;
            } else if (cur == 1) {
                modify = low + 1;
            } else {
                modify = (int) Math.pow(10, i - 1);
            }
            result += base + modify;
            i++;
        }
        return result;
    }


    /**
     * 主要思路：设定整数点（如1、10、100等等）作为位置点i（对应n的各位、十位、百位等等），分别对每个数位上有多少包含1的点进行分析
     根据设定的整数位置，对n进行分割，分为两部分，高位n/i，低位n%i

     当i表示百位，且百位对应的数>=2,如n=31456,i=100，则a=314,b=56，此时百位为1的次数有a/10+1=32（最高两位0~31），每一次都包含100个连续的点，即共有(a%10+1)*100个点的百位为1

     当i表示百位，且百位对应的数为1，如n=31156,i=100，则a=311,b=56，此时百位对应的就是1，则共有a%10(最高两位0-30)次是包含100个连续点，当最高两位为31（即a=311），本次只对应局部点00~56，共b+1次，所有点加起来共有（a%10*100）+(b+1)，这些点百位对应为1

     当i表示百位，且百位对应的数为0,如n=31056,i=100，则a=310,b=56，此时百位为1的次数有a/10=31（最高两位0~30）

     综合以上三种情况，当百位对应0或>=2时，有(a+8)/10次包含所有100个点，还有当百位为1(a%10==1)，需要增加局部点b+1

     之所以补8，是因为当百位为0，则a/10==(a+8)/10，当百位>=2，补8会产生进位位，效果等同于(a/10+1)
     */
    public int NumberOf1Between1AndN_Solution2(int n) {
        int count = 0;
        for (int i = 1; i <= n; i *= 10) {
            int a = n / i,b = n % i;
            //之所以补8，是因为当百位为0，则a/10==(a+8)/10，
            //当百位>=2，补8会产生进位位，效果等同于(a/10+1)
            count += (a + 8) / 10 * i + ((a % 10 == 1) ? b + 1 : 0);
        }
        return count;
    }
}