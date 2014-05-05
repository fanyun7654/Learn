package com.chen.file.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;



public class Picture {
    public static void main(String[] args){
        long start = 200700000000L;
        for(int i = 1;i <= 500;i++){
            String res = downloadFromUrl(start,"http://jwxt.zhongxi.cn/jwweb/_photo/Student/"+start+".jpg","d:/temp/2007/2007");
            start++;
        }
    }

    private static String downloadFromUrl(long start,String url, String dir) {
        try {
        	URL httpurl = new URL(url);
        	File f = new File(dir+start+".jpg");
			FileUtils.copyURLToFile(httpurl, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
    
}
