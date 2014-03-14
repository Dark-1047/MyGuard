package com.alan.myguard.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSInfoProvider {
	private static GPSInfoProvider gpsInfoProvider;
	private static Context context;
	private static MyLocationListener listener;
	private LocationManager locationManager;
	
	private GPSInfoProvider(){
		
	}
	
	public static synchronized GPSInfoProvider getInfoProvider(Context context){
		if(gpsInfoProvider==null){
			gpsInfoProvider=new GPSInfoProvider();
			GPSInfoProvider.context=context;
		}
		return gpsInfoProvider;
	}
	
	public String getLocation(){
		locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		String provider=get
	}
	
	//获取地理位置
	private String getBestProvider(){
		Criteria criteria=new Criteria();
		 //Criteria.ACCURACY_COARSE  这个是一般的定位
        //Criteria.ACCURACY_FINE  这个是精准定位
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是不是对海拔敏感的
        criteria.setAltitudeRequired(false);
        //设置对手机的耗电，定位要求越高，越耗电
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        //设置对速度变化是不是敏感
        criteria.setSpeedRequired(true);
        //设置在定位时，是不是允许与运营商交换数据的开销
        criteria.setCostAllowed(true);
        //这个方法是用来得到最好的定位方式的，它有两个参数，一个是Criteria(类似于Map集合)，就是一些条件，比如说对加速度敏感啊，什么海拔敏感这些的
        //第二个参数就是，如果我们置为false，那么我们得到的也有可能是已经关掉了的设备，如果是true那么，就只会得到已经打开了的设备
        return locationManager.getBestProvider(criteria, true);
	}
	
	public void stopGPSListener(){
		if(locationManager!=null){
			locationManager.removeUpdates(getLocation());
		}
	}

	private class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			//手机位置发生变法是调用的方法
			String latitude="纬度"+location.getLatitude();
			String longitude="经度"+location.getLongitude();
			SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
			Editor editor=sp.edit();
			editor.putString("lastLocation",latitude+"-"+longitude);
			editor.commit();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			//定位设备的状态发生改变的时候调用的方法，比如说用户把设备打开，或关闭，第二个参数就是设备的状态啦
		}

		@Override
		public void onProviderEnabled(String provider) {
			//设备打开的时候调用的方法
		}

		@Override
		public void onProviderDisabled(String provider) {
			  //设备关闭的时候调用的方法
		}
		
	}
}
