package com.wuhen.game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    public static void writeRecord(String score) {
        Properties properties = new Properties();
        //输入流（写输入流）
        try {
            FileOutputStream fos = new FileOutputStream("src/prop.properties");
            properties.setProperty("record", score);
            properties.store(fos, "");
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Properties properties = new Properties();

        //读取 输入流（读取输入流）
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("prop.properties");

        try {
            //属性对象加载
            properties.load(is);
            String record = properties.getProperty("record");
            System.out.println(record);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //输出流（写输出流）
        try {
            FileOutputStream fos = new FileOutputStream("src/prop.properties");
            properties.setProperty("record", "999");
            properties.store(fos, "");
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
