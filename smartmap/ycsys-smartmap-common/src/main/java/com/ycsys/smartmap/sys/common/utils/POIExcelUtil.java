package com.ycsys.smartmap.sys.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ycsys.smartmap.sys.common.exception.SysException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * * 
 * @version 1.0
 * @description  解析excel
 * @author lixiaoxin
 * @created 2016-11-15
 */

public class POIExcelUtil {

    /** 总行数 */
    private int totalRows = 0;

    /** 总列数 */
    private int totalCells = 0;


    /**限制的总行数*/
    private int limitTotalRows =500;

    /** 构造方法 */
    public POIExcelUtil()
    {}

    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * <ul>
     * <li>Description:[根据文件名读取excel文件]</li>
     * <li>Created by [Huyvanpull] [Jan 20, 2010]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @param fileName
     * @return
     * @throws SysException
     * @throws Exception
     */
    public List<ArrayList<String>> read(String fileName) throws SysException
    {
        List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

        try
        {
            boolean isExcel2003 = true;
            /** 对文件的合法性进行验证 */
            if (fileName.matches("^.+\\.(?i)(xlsx)$"))
            {
                isExcel2003 = false;
            }

            File file = new File(fileName);
            /** 调用本类提供的根据流读取的方法 */
            dataLst = read(new FileInputStream(file), isExcel2003);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        /** 返回最后读取的结果 */
        return dataLst;
    }

    /**
     * <ul>
     * <li>Description:[根据流读取Excel文件]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @param inputStream
     * @param isExcel2003
     * @return
     * @throws SysException
     */
    public List<ArrayList<String>> read(InputStream inputStream,boolean isExcel2003) throws SysException
    {
        List<ArrayList<String>> dataLst = null;
        try
        {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream)
                    : new XSSFWorkbook(inputStream);
            dataLst = read(wb);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dataLst;
    }

    /**
     * <ul>
     * <li>Description:[得到总行数]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @return
     */
    public int getTotalRows()
    {
        return totalRows;
    }

    /**
     * <ul>
     * <li>Description:[得到总列数]</li>
     * <ul>
     *
     * @return
     */
    public int getTotalCells()
    {
        return totalCells;
    }

    /**
     * <li>Description:[读取数据]</li>
     *
     * @param wb
     * @return
     * @throws SysException
     */
    public List<ArrayList<String>> read(Workbook wb) throws SysException
    {
        List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (this.totalRows >= 1 && sheet.getRow(0) != null)
        {
            //用第一行的单元格个数来确定列数不是很好，取起始位置中间位置以及最好位置的行数的最大值似乎更好。
            int startCells = sheet.getRow(0).getLastCellNum();
            int endCells = sheet.getRow(this.totalRows-1) == null ? 0:sheet.getRow(this.totalRows-1).getLastCellNum();
            int middleCells = sheet.getRow(this.totalRows/2) == null ? 0 : sheet.getRow(this.totalRows/2).getLastCellNum();
            this.totalCells = (startCells>=middleCells)?(startCells>=endCells?startCells:endCells):(middleCells>=endCells?middleCells:endCells);
        }

        /** 循环Excel的行  zheng.sk,如果去掉第一行，则从1开始循环*/
        for (int r = 1; r < this.totalRows; r++)
        {
            Row row = sheet.getRow(r);
            //如果每行第一列为空则默认这列都为空
            if (row == null || row.getCell(0) == null)
            {
                continue;
            }

            ArrayList<String> rowLst = new ArrayList<String>();

            /** 循环Excel的列   */
            for (short c = 0; c < this.getTotalCells(); c++)
            {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (cell == null)
                {
                    rowLst.add(cellValue);
                    continue;
                }
                //zheng.sk  对于数字的类型转换
                if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                    //读取excel整数的时候默认会加个小数点,为了不让加小数点特作此判断
                    DecimalFormat df = new DecimalFormat("0.000");
//                    String numberValue = String.valueOf(cell.getNumericCellValue());
                    String numberValue = df.format(cell.getNumericCellValue());//防止读取成为科学计数法
                    if(numberValue.contains(".")){
                        String[] splitStr = numberValue.split("\\.");
                        String round  = splitStr[0];
                        String decimal  = splitStr[1];
                        if(HSSFDateUtil.isCellDateFormatted(cell)){
                            //由于日期在Excel内是以double值存储的，所以日期格式要特别注意。统一转换成为"yyyy-MM-dd HH:mm:ss"格式
                            rowLst.add(DateUtils.dateFormat(cell.getDateCellValue()));
                        } else if (Long.valueOf(decimal) == 0){
                            rowLst.add(round);
                        }else {
                            rowLst.add(String.valueOf(df.format(cell.getNumericCellValue())));
                        }
                    }else{
                        rowLst.add(numberValue);
                    }
                }else{
                    rowLst.add(cell.getStringCellValue());
                }
            }

            //如果某行全部列的值都为空或者空字符串则认为此行为空，不做处理
            boolean isNotEmpty = false;
            for (String cellValue : rowLst) {
                if (StringUtils.isNotBlank(cellValue))isNotEmpty = true;
            }

            if (isNotEmpty){
                dataLst.add(rowLst);
            }
        }
        return dataLst;
    }
    /**
     *<li>Description:[读取数据],第n行开始读取</li>
     * @param wb
     * @return
     * @throws SysException
     */
    public List<ArrayList<String>> read2(Workbook wb,int index) throws SysException
    {
        List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        //总共几行
        this.totalRows = sheet.getPhysicalNumberOfRows()+index+1;
        //总共几列
        this.totalCells = sheet.getRow(index).getLastCellNum();

        /** 循环Excel的行  zheng.sk,如果去掉第一行，则从1开始循环*/
        for (int r = index; r < this.totalRows; r++)
        {
            Row row = sheet.getRow(r);
            //如果每行第一列为空则默认这列都为空
            if (row == null || row.getCell(0) == null)
            {
                continue;
            }

            ArrayList<String> rowLst = new ArrayList<String>();

            /** 循环Excel的列   */
            for (short c = 0; c < this.getTotalCells(); c++)
            {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (cell == null)
                {
                    rowLst.add(cellValue);
                    continue;
                }
                //zheng.sk  对于数字的类型转换
                if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                    //读取excel整数的时候默认会加个小数点,为了不让加小数点特作此判断
                    DecimalFormat df = new DecimalFormat("0.000");
//                    String numberValue = String.valueOf(cell.getNumericCellValue());
                    String numberValue = df.format(cell.getNumericCellValue());//防止读取成为科学计数法
                    if(numberValue.contains(".")){
                        String[] splitStr = numberValue.split("\\.");
                        String round  = splitStr[0];
                        String decimal  = splitStr[1];
                        if (Long.valueOf(decimal) == 0){
                            rowLst.add(round);
                            //由于日期在Excel内是以double值存储的，所以日期格式要特别注意。统一转换成为"yyyy-MM-dd HH:mm:ss"格式
                        }else if(HSSFDateUtil.isCellDateFormatted(cell)){
                            rowLst.add(DateUtils.dateFormat(cell.getDateCellValue()));
                        }else {
                            rowLst.add(String.valueOf(df.format(cell.getNumericCellValue())));
                        }
                    }else{
                        rowLst.add(numberValue);
                    }
                }else{
                    rowLst.add(cell.getStringCellValue());
                }
            }

            //如果某行全部列的值都为空或者空字符串则认为此行为空，不做处理
            boolean isNotEmpty = false;
            for (String cellValue : rowLst) {
                if (StringUtils.isNotBlank(cellValue))isNotEmpty = true;
            }

            if (isNotEmpty){
                dataLst.add(rowLst);
            }
        }
        return dataLst;
    }

    public void validateExcel(String  fileName) throws SysException, FileNotFoundException, IOException{
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$"))
        {
            throw new SysException("文件不是excel格式");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$"))
        {
            isExcel2003 = false;
        }

        File file = new File(fileName);
        Workbook wb = isExcel2003 ? new HSSFWorkbook(new FileInputStream(file))
                : new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = wb.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if((totalRows-1)>limitTotalRows){
            file.delete();
            throw new SysException("excel数据总行数不能超过"+limitTotalRows+"");
        }
        if(totalRows<2){
            file.delete();
            throw new SysException("excel数据总行数不能少于1");
        }

    }

    public Workbook validateExcel(String  fileName,InputStream is) throws SysException, FileNotFoundException, IOException{
      return this.validateExcel(fileName,is,limitTotalRows);
    }


    /**
     *
     * @param fileName
     * @param is
     * @param maxRows 允许最大行数
     * @return
     * @throws SysException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Workbook validateExcel(String  fileName,InputStream is,Integer maxRows) throws SysException, FileNotFoundException, IOException{
        if(maxRows==null || maxRows<=0){
            maxRows=limitTotalRows;
        }

        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$"))
        {
            throw new SysException("文件不是excel格式");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$"))
        {
            isExcel2003 = false;
        }

        Workbook wb = isExcel2003 ? new HSSFWorkbook(is): new XSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if((totalRows-1)>maxRows){
            throw new SysException("excel数据总行数不能超过"+maxRows+"");
        }
        if(totalRows<2){
            throw new SysException("excel数据总行数不能少于1");
        }
        return wb;
    }

    /**
     * <ul>
     * <li>Description:[测试main方法]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        List<ArrayList<String>> dataLst = new POIExcelUtil().read("d:/aa.xls");
        System.out.println("rowSize:"+dataLst.size());
        for(int i=0;i<dataLst.size();i++){
            ArrayList<String> cellLst = dataLst.get(i);
            System.out.println("cellSize:"+cellLst.size());
            for(int j=0;j<cellLst.size();j++){
                System.out.print(cellLst.get(j)+"|");
            }
        }
        System.out.println("OK");
    }
}
