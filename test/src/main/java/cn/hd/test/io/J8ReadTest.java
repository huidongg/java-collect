package cn.hd.test.io;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class J8ReadTest {
    public static void main(String[] args) throws Exception{
        List<String> s = Files.readAllLines(Paths.get("test/a.txt"));
        System.out.println(s.stream().map(x->Integer.valueOf(x)).reduce(Integer::sum).get());
    }
}
