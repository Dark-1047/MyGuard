package com.alan.myguard.engine;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.alan.myguard.mode.UpdateInfo;
//解析版本更新xml文件
public class UpdateInfoParser {
	public static UpdateInfo getUpdateInfo(InputStream is)throws Exception{
		UpdateInfo info=new UpdateInfo();
		XmlPullParser xmlParser=Xml.newPullParser();
		//设置输入流，并制定编码方式
		xmlParser.setInput(is,"utf-8");
		int type=xmlParser.getEventType();
		while(type!=XmlPullParser.END_DOCUMENT){
			switch(type){
			case XmlPullParser.START_TAG:
				if(xmlParser.getName().equals("version")){
					info.setVersion(xmlParser.nextText());
				}else if(xmlParser.getName().equals("description")){
					info.setDescription(xmlParser.nextText());
				}else if(xmlParser.getName().equals("apkurl")){
					info.setUrl(xmlParser.nextText());
				}
				break;
		    default:
					break;
			}
			type=xmlParser.next();
		}
		return info;
	}
}
