package com.alan.myguard.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alan.myguard.mode.UpdateInfo;

import android.content.Context;
//版本更新逻辑处理
public class UpdateInfoService {
	private Context context;
	public UpdateInfoService(Context context){
		this.context=context;
	}
	//
	public UpdateInfo getUpdateInfo(int urlId) throws Exception{
		
			//拿到config.xml.
			String path = context.getResources().getString(urlId);
			URL url = new URL(path);
			//启动一个http连接
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			//设置链接超时时间
			httpURLConnection.setConnectTimeout(5000);
			//设置请求方式
			httpURLConnection.setRequestMethod("GET");
			
			InputStream is=null;
			try {
				is = httpURLConnection.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return UpdateInfoParser.getUpdateInfo(is);
	}
	
}
