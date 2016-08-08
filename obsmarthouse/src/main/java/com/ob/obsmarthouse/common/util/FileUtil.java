package com.ob.obsmarthouse.common.util;

import android.graphics.Bitmap;

import com.ob.obsmarthouse.common.bean.localbean.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileUtil {

    /**
     * @param fileName 文件夹名称
     * @return 生成的文件夹
     */
    public static File creatSDFile(String fileName) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 创建文件夹
     *
     * @param dirName 文件路径
     */
    public static boolean creatSDDir(String dirName) {
        File dir = new File(dirName);
        return dir.mkdirs();
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName 文件名称
     * @return 存在返回true，不存在返回false
     */
    public static boolean isFileExist(String fileName) {
        try {
            File file = new File(fileName);
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 将Bitmap文件保存到本地文件
     *
     * @param file  文件名
     * @param photo 要保存的bitmap
     */
    public static File saveFile(File file, Bitmap photo) {
        FileOutputStream fOut = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fOut = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            if (fOut != null) {
                fOut.flush();
                fOut.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**获取文件中的昂宝定义版本
     * @param file 目标文件
     * @return Version 参考协同使用MathUtil的canUpdate函数
     */
    public static Version getVersionOfFile(File file) {
        Version mVersion;
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (input == null) {
            return null;
        }
        byte[] hardVersion = new byte[6];
        byte[] version = new byte[15];
        try {
            input.read(hardVersion);
            input.read(version);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int readfirstVersion = version[13];
        int readsecondVersion = version[14];
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mVersion = new Version(hardVersion, readfirstVersion, readsecondVersion);
        return mVersion;
    }
}
