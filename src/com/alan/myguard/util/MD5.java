package com.alan.myguard.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String encode(String pwd){
		//拿到MD5加密对象
		try {
			MessageDigest messageDigest=MessageDigest.getInstance("MD5");
			//返回一个加密后的字节数组
			byte[] bytes=messageDigest.digest(pwd.getBytes());
			StringBuffer sb=new StringBuffer();
			String tmp;
			for(int i=0;i<bytes.length;i++){
				tmp=Integer.toHexString(0xff&bytes[i]);
				if(tmp.length()==1){//如果这个字符串,只有一个字符就要补0
					sb.append("0"+tmp);
				}else{
					sb.append(tmp);
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有这个加密算法");
		}
	}
}
