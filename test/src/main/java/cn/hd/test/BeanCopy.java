package cn.hd.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

public class BeanCopy {
    public static void main(String[] args) {
//        ExaminationReportNofifyResponseDTO dto = new ExaminationReportNofifyResponseDTO();
//        dto.setEagleEyeTraceId("123");
//        ExaminationReportNofifyResponseDTO.ReportResponse response = new ExaminationReportNofifyResponseDTO.ReportResponse();
//        response.setMsg("sdfsd");
//        response.setResponseCode("9527");
//        dto.setData(response);
//        dto.setAge("111");
//        String dtoStr = JSON.toJSONString(dto);
//        Edto edto = JSONObject.parseObject(dtoStr, Edto.class);
//        System.out.println(JSON.toJSONString(edto));


        Map<String, Object> map = new HashMap<>();
        map.put("age", "111");
        Ext ext = new Ext();
        BeanUtils.copyProperties(map, ext, Ext.class);
        System.out.println(JSON.toJSONString(ext));

    }

    @Data
    public static class Ext {
        private String age;
    }

    @Data
    public static class ExaminationReportNofifyResponseDTO extends Ext {
        private ReportResponse data;
        // 鹰眼id
        private String eagleEyeTraceId;

        @Data
        public static class ReportResponse {
            private String msg;
            private String responseCode;
        }
    }

    @Data
    public static class Edto extends Ext {
        private ReportResponse data;
        // 鹰眼id
        private String eagleEyeTraceId;

        @Data
        public static class ReportResponse {
            private String msg;
            private String responseCode;
        }
    }
}
