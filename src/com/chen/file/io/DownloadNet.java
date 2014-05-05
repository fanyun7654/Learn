package com.chen.file.io;

import java.io.BufferedInputStream; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class DownloadNet implements Runnable {

	static final boolean DEBUG = false;
	static int DownloadSpeed = 0;
	static boolean fo = true;

	public static void main(String[] args) {

		try {
			for(int i =1 ;i < 10000;i++){
				DownloadNet downloadApk = new DownloadNet();
				Thread thread = new Thread(downloadApk);
				thread.start();                
				if(downloadApk.toDownload(String.valueOf(i))){
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean toDownload(String count) {
//		String url = "http://dragon.dl.hoolaigames.com/Product_APK/DC_ANDROID_360Qihoo.apk"; // 下载文件地址
		String url = "http://3gimg.qq.com/web/webapp_webmarket/android/images/pop-cloud.jpg"; // 下载文件地址
		String file = "e:/download/"+count+".apk"; // 保存地址

		try {
			return saveToFile(url, file);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean saveToFile(String addressUrl, String fileName) throws IOException {
		boolean DEBUG = true;// 是否输出调试信息
		int BUFFER_SIZE = 51200;// 缓冲区大小
		long AllSize = 0;
		DecimalFormat twoDigits = new DecimalFormat("0.00");  //关于DecimalFormat http://book.51cto.com/art/200907/140729.htm

		FileOutputStream fileOutputStream = null; //关于 FileOutpuStream   http://www.cnblogs.com/jjtech/archive/2011/04/17/2019210.html
		BufferedInputStream bufferedInputStream = null; //缓冲输入流
		HttpURLConnection httpUrl = null;   
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE]; // 缓冲区
		int downloadSize = 0;

		// 建立链接
		url = new URL(addressUrl);
		httpUrl = (HttpURLConnection) url.openConnection(); 
		// 连接指定的资源
		long count = Long.parseLong(httpUrl.getHeaderField("Content-Length")); // 获取要下载文件的总大小(字节)
		//parseLong将一个字符串转换成数字
		httpUrl.connect();
		// 获取网络输入流
		bufferedInputStream = new BufferedInputStream(httpUrl.getInputStream());
		// 建立文件
		File file = new File(fileName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		fileOutputStream = new FileOutputStream(fileName);

		if (DEBUG)
			System.out.println("正在获取链接[" + addressUrl + "]的内容...\n将其保存为文件["+ fileName + "]");

		// 保存文件
		while ((downloadSize = bufferedInputStream.read(buf)) != -1) {
			fileOutputStream.write(buf, 0, downloadSize);
			AllSize += downloadSize; // size是循环一次下载的字节数.所以.我们每次相加.得到AllSize 就知道当前下载了多少字节了.方便等下算文件下载的百分比
			DownloadSpeed += downloadSize; // 因为我们下面的线程会3秒钟自动读取一次DownloadSpeed的值.
			// 这样我们好知道1秒下载了多少.好算出来下载的速度..
			// ---------------------看不到下载的速度.因为此线程输出速度太快.屏蔽下面的输出.---------------------
			System.out.println("downloadSize=" + downloadSize + "-----AllSize=" + AllSize);
			System.out.println("进度"+ (twoDigits.format((double) (AllSize * 100) / count))+"%");
		}
		fo = false;
		fileOutputStream.close();
		bufferedInputStream.close();
		httpUrl.disconnect();
		return true;
	}

	public void run() {

		while (fo) {
			System.out.println("DownloadSpeed=" + DownloadSpeed + " 下载速度" + ((DownloadSpeed/1024)/3)); // 我设置3秒算一次.这样会准确点.得到的是KB
			DownloadSpeed = 0;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 

	}
}