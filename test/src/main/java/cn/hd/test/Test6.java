package cn.hd.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Stack;

public class Test6 {
    public static void main(String[] args) {
        String x = "http://vcoser-deploy.vcoser.com.cn/videos/20210507/cut_500365367852933120_1620379464.mp4";
        String y = x.replaceAll("(http)(.*?)(.cn/|.com/)", "");
        System.out.println(y);

        String z = "abcd/aaa.txt";
        String zz = z.substring(z.lastIndexOf("/") + 1);
        System.out.println(zz);

        String a = "({()}){[()]}";

        System.out.println(isValid(a));
//        int [] arr = {1,3,4,8}; int [][] queries = {{0,1},{1,2},{0,3},{3,3}};
        int [] arr = {16}; int [][] queries = {{0,0},{0,0}, {0,0}};
//        int [] arr = {4,8,2,10}; int [][] queries = {{2,3},{1,3},{0,0},{0,3}};
        System.out.println(JSON.toJSONString(xorQueries(arr, queries)));

        test();
    }

    public static boolean isValid(String s) {
        if (s.length()%2 == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)=='('
                    || s.charAt(i) == '{'
                    || s.charAt(i) == '[') {
                stack.push(s.charAt(i));
            } else if (s.charAt(i)==')') {
                if (!stack.empty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }
            }else if (s.charAt(i)=='}') {
                if (!stack.empty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }
            }else if (s.charAt(i)==']') {
                if (!stack.empty() && stack.peek() == '[') {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    public static int[] xorQueries(int[] arr, int[][] queries) {
        int [] rt = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            if (queries[i][0] == queries[i][1]) {
                rt[i] = arr[queries[i][0]];
            } else {
                int tmp = arr[queries[i][0]];
                for (int j = queries[i][0] + 1; j <=queries[i][1]; j++) {
                    tmp = tmp ^ arr[j];
                }
                rt[i] = tmp;
            }
        }
        return rt;
    }
    
    private static void test () {
        String unityContent = "{\"m_plotFrameList\":[{\"m_IsSpeakAside\":false,\"m_speakAside\":\"\",\"m_background\":\"\",\"m_IsPureColour\":false,\"m_ColourR\":128,\"m_ColourG\":128,\"m_ColourB\":128,\"m_durtion\":0.0,\"m_speed\":0.0,\"m_audio\":\"\",\"m_audioLoop\":false,\"m_roles\":[{\"m_name\":\"黑白熊\",\"m_action\":\"2968\",\"m_audio\":\"\",\"m_model\":\"m537667175616081920\",\"m_subtitles\":\"\",\"m_birthPoint\":\"2\",\"m_photo\":\"\",\"m_gifPhoto\":\"\",\"m_isLoop\":\"20\",\"m_moveTime\":\"0\",\"m_isMove\":\"20\",\"m_scale\":\"2.6\",\"m_lineNo\":\"1\",\"m_morph\":\"\",\"m_morphspeed\":0.0,\"m_cameraPos\":{\"x\":-0.10000000149011612,\"y\":1.600000023841858,\"z\":2.240000009536743},\"m_cameraRot\":{\"x\":18.132999420166017,\"y\":-180.0,\"z\":0.0},\"m_fancyCamera\":0}],\"m_effects\":[],\"m_CameraInfo\":{\"m_CameraPos\":{\"x\":-0.10000000149011612,\"y\":1.600000023841858,\"z\":2.240000009536743},\"m_CameraRot\":{\"x\":18.132999420166017,\"y\":-180.0,\"z\":0.0}},\"m_actorItem\":[],\"m_preview\":\"\",\"m_frameTime\":0.0,\"_isData\":\"已修改\",\"m_backgif\":\"\",\"m_forgif\":\"\",\"badCount\":0,\"recordTime\":0}]}";
        JSONObject jsonObject = JSONObject.parseObject(unityContent);
        String content = jsonObject.getString("m_plotFrameList");

    }
}
