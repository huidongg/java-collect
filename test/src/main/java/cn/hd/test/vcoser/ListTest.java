package cn.hd.test.vcoser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
//        RestTemplate r = new RestTemplate();
//        for (Long startTime = 1621008000000L; startTime <= 1623081600000L; startTime+=24 * 3600 * 1000) {
//            r.getForObject("http://10.163.1.21:14000/list/V1.6.8/calculateInit?dateParam={1}", RestModel.class, startTime);
//        }

//        String x = "20210609";
//        System.out.println(timeToLong(x));

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        if (list.stream().noneMatch(x->x.equals(3))) {
            System.out.println("a");
        }
    }

    /**
     * 将字符串转日期成Long类型的时间戳，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static Long timeToLong(String time) {
        SimpleDateFormat sim=new SimpleDateFormat("yyyyMMdd");
        long s=0;
        try {
            s=sim.parse(time).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }
}
