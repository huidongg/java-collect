package cn.hd.leetcode.normal._布隆过滤器;

/**
 * BloomFilter  做的是hash的映射, 因为hash碰撞的原因, 存在误判率, 所以增加多个hash函数计算.所以可以[判断一定不存在]
 * 快速定位
 * 删除元素的第一种方案:
 * 1, 计数器, 当进行存储的时候,i++
 * 2, 不管
 */
public class Test {
    public static void main(String[] args) {
        // 1101 = 13
        // 0011
        System.out.println(13>>2);
        System.out.println(13/4);
        System.out.println(13%4);
        System.out.println(13&(4 -1));
    }


}
