package com.alan.myguard.util;

import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class EditorUtil {
	public static void stringCommit(String key,String value,SharedPreferences sp){
		Editor ed=sp.edit();
		ed.putString(key,value);
		ed.commit();
	}
	
	public static void intCommit(String key,int value,SharedPreferences sp){
		Editor ed=sp.edit();
		ed.putInt(key,value);
		ed.commit();
	}
	
	public static void stringSetCommit(String key,Set<String> values,SharedPreferences sp){
		Editor ed=sp.edit();

		ed.commit();
	}
	

}
