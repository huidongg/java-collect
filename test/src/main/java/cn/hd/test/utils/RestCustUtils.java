package cn.hd.test.utils;


import cn.hd.test.facebank.Flowing;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by laosan on 2018/5/16 0016.
 * rest util
 */
public class RestCustUtils {


    /**
     *
     * @param rest
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T getModel(Flowing.RestModel rest, Class<T> clazz) {
        if (rest != null && rest.getCode() != null) {
            if ("200".equals(rest.getCode())) {
                return JSON.parseObject(JSON.toJSONString(rest.getData()), clazz);
            } else {
                 throw new RuntimeException("rest service fail, return non 200, cause:" + rest.getMessage());
            }
        } else {
            throw new RuntimeException("rest service fail, rest or code is null, cause:" + rest.getMessage());
        }
    }

    public static <T> List<T> getModelList(Flowing.RestModel rest, Class<T> clazz) {
        if (rest != null && rest.getCode() != null) {
            if ("200".equals(rest.getCode())) {
                return JSON.parseArray(JSON.toJSONString(rest.getData()), clazz);
            } else {
                throw new RuntimeException("rest service fail, return non 200, cause:" + rest.getMessage());
            }
        } else {
            throw new RuntimeException("rest service fail, rest or code is null, cause:" + rest.getMessage());
        }
    }


}
