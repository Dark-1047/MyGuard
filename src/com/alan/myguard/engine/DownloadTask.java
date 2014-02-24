package com.alan.myguard.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;

public class DownloadTask {
	public static File getFile(String path,String filePath,ProgressDialog progressDialog)throws Exception{
		URL url=new URL(path);
		//打开一个链接
		HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
		httpURLConnection.setConnectTimeout(5000); //设置链接超时时间
		httpURLConnection.setRequestMethod("GET"); //设置请求方式
		if(httpURLConnection.getResponseCode()==200){//返回http相应代码
			//获取文件的长度
			int total=httpURLConnection.getContentLength();
			//设置加载耗时时间
			progressDialog.setMax(total);
			InputStream is=httpURLConnection.getInputStream();
			File file=new File(filePath);
			FileOutputStream fos=new FileOutputStream(file);
			byte[] buffer=new byte[1024];
			int len;
			int process=0;
			while((len=is.read(buffer))!=-1){
				fos.write(buffer,0,len);
				process+=len;
				//设置进度
				progressDialog.setProgress(process);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		}
		return null;
	}
}
