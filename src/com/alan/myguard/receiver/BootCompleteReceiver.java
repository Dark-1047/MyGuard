package com.alan.myguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class BootCompleteReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
		boolean isProtected=sp.getBoolean("isProtected", false);
		//看看是不是开启了保护
		if(isProtected){
			TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			//开机后，拿到当前sim卡的标示，与我们之前存放的标示对比
			String currentSim=telephonyManager.getSimSerialNumber();
			String protectedSim=sp.getString("simSerial", "");
			if(!currentSim.equals(protectedSim)){
				//拿到一个短信的管理器
				SmsManager smsManager=SmsManager.getDefault();
				String number=sp.getString("number","");
				//发送短信，有5个参数，第一个是要发送的地址，第二个是发送人，可以设置null
				//第三个是要发送的信息，第四个是发送状态，第五个是发送后的，都可以设置null
				smsManager.sendTextMessage(number, null, "Sim卡已经变更了", null,null);
			}
		}
	}

}
