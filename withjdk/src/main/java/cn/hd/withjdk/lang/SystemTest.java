package cn.hd.withjdk.lang;

import com.alibaba.fastjson.JSON;

public class SystemTest {
    public static void main(String[] args) {
        arrayCopy();
    }

    /**
     * public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
     * Object src : 原数组
     * int srcPos : 从元数据的起始位置开始
     * Object dest : 目标数组
     * int destPos : 目标数组的开始起始位置
     * int length  : 要copy的数组的长度
     */
    private static void arrayCopy() {
        // 用处1, 数组拷贝
        Integer [] ar1 = {1, 2, 3, 4, 5};
        Integer [] ar2 = new Integer[3];
        System.arraycopy(ar1, 2, ar2, 0, 3);
        System.out.println(JSON.toJSONString(ar2));
        // 用处2, 数组剪切(将空元素放到最后, ArrayList的remove用法)
        // 删除掉数组第3个元素
        int delIndex = 3;
        int len = ar1.length;
        if (delIndex < len -1)
            System.arraycopy(ar1, delIndex, ar1, delIndex+1, len - 1 - delIndex);
        ar1[len - 1] = null;
        System.out.println(JSON.toJSONString(ar1));
    }
}
