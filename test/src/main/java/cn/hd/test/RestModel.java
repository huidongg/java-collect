package cn.hd.test;

import lombok.Data;

@Data
public class RestModel {
    public static final String CODE_SUCCESS = "200";
    public static final String MESSAGE_SUCCESS = "成功";
    /**
     * 返回代码
     */
    private String code;
    /**
     * 返回的内容
     */
    private String message;
    /**
     * 返回附带的实体内容
     */
    private Object data;
}
