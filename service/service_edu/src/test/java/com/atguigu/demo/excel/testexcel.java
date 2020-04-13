package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.ExcelBuilder;

import java.util.ArrayList;
import java.util.List;

public class testexcel {

    public static void main(String[] args) {

        //写excel
//        String filename="F:\\testexcel.xlsx";
//
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
        //都excel
        String filename="F:\\testexcel.xlsx";
        EasyExcel.read(filename,DemoData.class,new Excellistner()).sheet().doRead();
    }
    private  static List<DemoData> getData(){

        List<DemoData> list=new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            DemoData data=new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);

        }

        return list;
    }


}
