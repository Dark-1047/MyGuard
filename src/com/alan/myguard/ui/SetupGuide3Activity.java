package com.alan.myguard.ui;

import com.alan.myguard.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetupGuide3Activity extends Activity implements OnClickListener {

	private Button bt_next;
	private Button bt_pervious;
	private Button bt_select;
	private EditText et_phoneNumber;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_guide3);
		sp=getSharedPreferences("config",Context.MODE_PRIVATE);
		bt_next=(Button)findViewById(R.id.bt_guide_next);
		bt_next.setOnClickListener(this);
		bt_pervious=(Button)findViewById(R.id.bt_guide_pervious);
		bt_pervious.setOnClickListener(this);
		bt_select=(Button)findViewById(R.id.bt_guide_select);
		bt_select.setOnClickListener(this);
		et_phoneNumber=(EditText)findViewById(R.id.et_guide_phoneNumber);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null){
			String number=data.getStringExtra("number");
			Log.i("test",number);
			et_phoneNumber.setText(number);
		}
	}
	
	@Override
	public void onClick(View v) {
		Log.i("test","进入事件");
		switch(v.getId()){
			case R.id.bt_guide_select:
				Intent selectIntent=new Intent(this,SelectContactActivity.class);
				//启动一个activity来获取数据，获取到的数据是在重写的onActivityResult
				startActivityForResult(selectIntent, 1);
				break;
			case R.id.bt_guide_next:
				String number=et_phoneNumber.getText().toString().trim();
				if(number.equals("")){
					Toast.makeText(this,"安全号码不能为空",Toast.LENGTH_SHORT);
				}else{
					Editor editor=sp.edit();
					editor.putString("numer", number);
					editor.commit();
					
					Intent intent=new Intent(this,SetupGuide4Activity.class);
					finish();
					startActivity(intent);
					overridePendingTransition(R.anim.translate_in,R.anim.translate_out);
				}
				break;
			case R.id.bt_guide_pervious:
				Intent i=new Intent(this,SetupGuide2Activity.class);
				finish();
				startActivity(i);
				overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
				break;
				default:
					break;
		}
	}

}
