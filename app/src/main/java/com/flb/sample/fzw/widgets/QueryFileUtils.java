package com.flb.sample.fzw.widgets;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.flb.sample.fzw.common.Constant;
import com.flb.sample.fzw.model.Material;
import com.flb.sample.fzw.model.Song;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static com.flb.sample.fzw.common.Constant.type.*;

/**
 * author : 冯张伟
 * date : 2019/4/19
 */
public class QueryFileUtils {




    public static ArrayList<Map<String, String>>  getFileAsType(String type){

        int mType = 0;

       //获得外部存储的根目录
        File dir = Environment.getExternalStorageDirectory();
        ArrayList<Map<String, String>> fileListData = new ArrayList<>();
        //全部文件 图片/音频/视频
        if (type.equals(FILE_ALL)){
            mType = FILE_TYPE_ALL;
        }
        //图片
        if (type.equals(FILE_IMAGE)){
            mType = FILE_TYPE_IMAGE;
        }
        //音频
        if (type.equals(FILE_MUSIC)){
            mType = FILE_TYPE_MUSIC;
        }
        //视频
        if (type.equals(FILE_VIDOE)){
            mType = FILE_TYPE_VIDEO;
        }
        if (type.equals(FILE_RESTS)){
            mType = FILE_TYPE_RESTS;
        }

        //调用遍历所有文件的方法
        recursionFile(dir,fileListData,mType);
        return fileListData;
    }


    public static boolean isImage(String name){
        if (name.endsWith(".BMP") ||
                name.endsWith(".JPEG") ||
                name.endsWith(".GIF") ||
                name.endsWith(".PSD") ||
                name.endsWith(".PNG")||
                name.endsWith(".TIFF")||
                name.endsWith(".TGA")||
                name.endsWith(".EPS")||
                name.endsWith(".bmp")||
                name.endsWith(".jpeg")||
                name.endsWith(".gif")||
                name.endsWith(".psd")||
                name.endsWith(".png")||
                name.endsWith(".tiff")||
                name.endsWith(".tga")||
                name.endsWith(".eps")){
            return true;
        }
        return false;
    }

    public static boolean isMusic(String name){
        if (name.endsWith(".mp3")){
            return true;
        }
        return false;
    }

    public static boolean isVideo(String name){
        if (name.endsWith(".mp4")){
            return true;
        }
        return false;
    }

    public static boolean restes(String name){
        if (name.endsWith(".BMP") ||
                name.endsWith(".JPEG") ||
                name.endsWith(".GIF") ||
                name.endsWith(".PSD") ||
                name.endsWith(".PNG")||
                name.endsWith(".TIFF")||
                name.endsWith(".TGA")||
                name.endsWith(".EPS")||
                name.endsWith(".bmp")||
                name.endsWith(".jpeg")||
                name.endsWith(".gif")||
                name.endsWith(".psd")||
                name.endsWith(".png")||
                name.endsWith(".tiff")||
                name.endsWith(".tga")||
                name.endsWith(".eps")||
                name.endsWith(".mp3")||
                name.endsWith(".mp4")){
            return true;
        }
        return false;
    }

    public static void recursionFile(File dir, ArrayList<Map<String, String>> fileListData,int type) {
        //得到某个文件夹下所有的文件
        File[] files = dir.listFiles();
        //文件为空
        if (files == null) {
            return;
        }
        //遍历当前文件下的所有文件
        for (File file : files) {
            //如果是文件夹
            if (file.isDirectory()) {
                //则递归(方法自己调用自己)继续遍历该文件夹
                recursionFile(file,fileListData,type);
            } else { //如果不是文件夹 则是文件   //如果文件名以 .mp3结尾则是mp3文件
                HashMap<String, String>  item = new HashMap<>();;
               if (type == Constant.type.FILE_TYPE_ALL){
                   item.put("fileName", file.getName());//文件名
                   item.put("filePath", file.getAbsolutePath());//文件路径
                   fileListData.add(item);
               }
               if (type == Constant.type.FILE_TYPE_IMAGE){
                   if (isImage(file.getName())) {
                       //往图片集合中 添加图片的路径
                       item.put("fileName", file.getName());//文件名
                       item.put("filePath", file.getAbsolutePath());//文件路径
                       fileListData.add(item);
                   }
               }
               if (type == Constant.type.FILE_TYPE_MUSIC){
                   if (isMusic(file.getName())) {
                       //往图片集合中 添加图片的路径
                       item.put("fileName", file.getName());//文件名
                       item.put("filePath", file.getAbsolutePath());//文件路径
                       fileListData.add(item);
                   }
               }

               if (type == Constant.type.FILE_TYPE_VIDEO){
                   if (isVideo(file.getName())) {
                       //往图片集合中 添加图片的路径
                       item.put("fileName", file.getName());//文件名
                       item.put("filePath", file.getAbsolutePath());//文件路径
                       fileListData.add(item);
                   }
               }

               if (type == Constant.type.FILE_TYPE_RESTS){
                   if (!restes(file.getName())) {
                       //往图片集合中 添加图片的路径
                       item.put("fileName", file.getName());//文件名
                       item.put("filePath", file.getAbsolutePath());//文件路径
                       fileListData.add(item);
                   }
               }
            }
        }
    }

}
