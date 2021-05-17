package cn.hd.test.utils;

import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: djchen
 * Date: 2020/8/31
 * Time: 14:08
 * Description:
 */
@Component
public class ExcelUtils {
    @Value("${report.file.path}")
    String filePath;
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    //使用poi生产并导入excel
    /**
     * 传入数据，在指定路径下生成Excel文件 支持生成多个sheet，并为sheet命名
     *
     * @param absolutePath    生成文件的绝对路径，例如"C:\\Users\\...\\out.xlsx"
     * @param dataForExcelMap key：sheet名； value：传入的数据 名字相同时会覆盖之前的文件
     * @return
     */
    public boolean generateExcelWithManySheets(String absolutePath, Map<String, List<DataForExcel>> dataForExcelMap) {
        boolean flag = false;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (Map.Entry<String, List<DataForExcel>> entry : dataForExcelMap.entrySet()) {
                XSSFSheet sheet = workbook.createSheet(entry.getKey());
                List<DataForExcel> dataForExcel = entry.getValue();
                Collections.sort(dataForExcel, (arg0, arg1) -> arg0.getRow().compareTo(arg1.getRow()));
                XSSFRow nrow = null;
                for (DataForExcel data : dataForExcel) {
                    if (dataForExcel.indexOf(data) == 0 || !data.getRow().equals(dataForExcel.get(dataForExcel.indexOf(data) - 1).getRow())) {
                        nrow = sheet.createRow(data.getRow());
                    }
                    XSSFCell ncell = nrow.createCell(data.getColumn());
                    ncell.setCellValue(data.getValue());
                }
            }
            File file = new File(absolutePath);
            file.createNewFile();
            FileOutputStream stream = FileUtils.openOutputStream(file);
            workbook.write(stream);
            stream.close();
            flag = true;
        } catch (Exception ie) {
            logger.info("生成excel失败，失败原因为:{}",ie);
        }
        return flag;
    }

    private String getFilePath(String fileName){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        File dir = new File(filePath);
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(filePath+today);
        if (!file.exists()){
            file.mkdir();
        }
        return filePath+today+"/"+fileName;
    }

    /**
     * 初始化文件
     * @param fileName 文件名称
     * @param sheetName sheet名称
     * @param dataForExcelList 文件头
     * @return
     */
    public String init(String fileName,String sheetName,List<DataForExcel> dataForExcelList) {
        Map<String, List<DataForExcel>> dataMap = new HashMap<>();
        dataMap.put(sheetName, dataForExcelList);
        String path = this.getFilePath(fileName);
        File oldFile = new File(path);
        if (!oldFile.exists()){
            boolean bln = this.generateExcelWithManySheets(path, dataMap);
            if (!bln){
                path = "";
            }
        }
        return path;
    }

    /**
     * 向已存在的excel中追加数据
     *
     * @param absolutePath 已存在的excel绝对路径
     * @param sheetIndex   sheet的序号，从0开始
     * @param dataList     cell数据
     * @return
     * @throws IOException
     */
    public Boolean addExcel(String absolutePath, int sheetIndex, List<String[]> dataList) throws IOException {
        int columnsNum = dataList.get(0).length;
        FileInputStream fs = new FileInputStream(absolutePath);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);
        XSSFRow row;
        int lastRowNum = sheet.getLastRowNum();
        FileOutputStream out = new FileOutputStream(absolutePath);
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(++lastRowNum);
            String[] addOneRowData = dataList.get(i);
            for (int j = 0; j < addOneRowData.length; j++) {
                String str = addOneRowData[j];
                row.createCell(j).setCellValue(str);
            }
        }
        setSheetStyle(sheet, columnsNum - 1);
        wb.write(out);
        out.flush();
        out.close();
        return true;
    }

    private XSSFSheet setSheetStyle(XSSFSheet sheet, int columnsNum) {
        sheet.createFreezePane(0, 1, 0, 1);
        String columnRange = "A1:" + (char) (65 + columnsNum) + "1";
        sheet.setAutoFilter(CellRangeAddress.valueOf(columnRange));
        for (int i = 0; i <= columnsNum; i++) {
            sheet.autoSizeColumn(i);
        }
        return sheet;
    }
    @Data
    public static class DataForExcel{
        Integer column;
        Integer row;
        String value;

        public DataForExcel(Integer row, Integer column, String value){
            this.column = column;
            this.row = row;
            this.value = value;
        }
    }

    /**
     *
     * @param path 初始化的时候返回的值
     * @param data
     * @throws IOException
     */
    public void inputFile(String path,List<String[]>data) throws IOException {
        this.addExcel(path, 0, data);
    }
    //测试类
    /*public static void main(String[] args) throws IOException {
        ExcelUtils excelUtils = new ExcelUtils();
        List<DataForExcel> dataForExcelList1 = new ArrayList<>();

        dataForExcelList1.add(new DataForExcel(0, 0, "序号"));
        dataForExcelList1.add(new DataForExcel(0, 1, "姓名"));
        dataForExcelList1.add(new DataForExcel(0, 2, "性别"));
        dataForExcelList1.add(new DataForExcel(0, 3, "家庭住址"));
        dataForExcelList1.add(new DataForExcel(0, 4, "通信地址"));
        dataForExcelList1.add(new DataForExcel(0, 5, "手机号"));
        String result = excelUtils.init("test.xlsx","测试",dataForExcelList1);
        System.out.println(result);
        List<String[]> testData = new ArrayList<>();
        for (int k = 1; k < 11; k++) {
            String[] oneRow = new String[6];
            oneRow[0] = k + "";
            oneRow[1] = "张三" + oneRow[0];
            oneRow[2] = "男";
            oneRow[3] = "北京市朝阳区";
            oneRow[4] = "北京市大兴区";
            oneRow[5] = (System.currentTimeMillis() % 10000000000L) + "";
            testData.add(oneRow);
        }
        excelUtils.inputFile("test.xlsx",testData);
    }*/
}
