package com.alan.myguard.ui;

import com.alan.myguard.R;
import com.alan.myguard.util.MD5;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LostProtectedActivity extends Activity implements OnClickListener{

	private SharedPreferences sp;
	private Dialog dialog;
	private EditText password;
	private EditText confirmPassword;
	private TextView tv_protectedNumber;
	private TextView tv_protectedGuide;
	private CheckBox cb_isProtected;
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("cofig",Context.MODE_PRIVATE);
		if(isSetPassword()){
			showLoginDialog();
		}else{
			showFirstDialog();
		}
	}
	
	//登陆界面
	private void showLoginDialog(){
		dialog=new Dialog(this,R.style.MyDialog);
		View view=View.inflate(this,R.layout.login_dialog,null);
		password=(EditText)view.findViewById(R.id.et_protected_password);
		Button yes=(Button)view.findViewById(R.id.bt_protected_login_yes);
		Button cancel=(Button)view.findViewById(R.id.bt_protected_login_no);
		yes.setOnClickListener(this);
		cancel.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();
	}
	
	//第一次登陆界面
	private void showFirstDialog(){
		dialog=new Dialog(this,R.style.MyDialog);
		View view=View.inflate(this,R.layout.first_dialog,null);
		password=(EditText)view.findViewById(R.id.et_protected_first_password);
		confirmPassword=(EditText)view.findViewById(R.id.et_protected_confirm_password);
		Button yes=(Button)view.findViewById(R.id.bt_protected_first_yes);
		Button cancel=(Button)view.findViewById(R.id.bt_protected_first_no);
		yes.setOnClickListener(this);
		cancel.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();
	}
	
	//判断密码是否为空
	private boolean isSetPassword(){
		String pwd=sp.getString("passsword","");
		if(pwd.equals("")||pwd==null){
			return false;
		}
		return true;
	}
	
	//判断是否安全引导设置
	private boolean isSetupGuide(){
		return sp.getBoolean("setupGuide",false);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bt_protected_first_yes:
			String fp=password.getText().toString().trim();
			String cp=confirmPassword.getText().toString().trim();
			if(fp.equals("")||cp.equals("")){
				Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
				return;
			}else{
				if(fp.equals(cp)){
					Editor editor=sp.edit();
					editor.putString("password",MD5.encode(fp));
					editor.commit();
					dialog.dismiss();
					if(!isSetupGuide()){
						finish();
						Intent intent=new Intent(this,SetupGuide1Activity.class);
						startActivity(intent);
					}
				}else{
					Toast.makeText(this,"两次密码不相同", Toast.LENGTH_SHORT).show();
				}
			}
			dialog.dismiss();
			break;
		case R.id.bt_protected_first_no:
			dialog.dismiss();
			finish();
			break;
		case R.id.bt_protected_login_yes:
			String pwd=password.getText().toString();
			if(pwd.equals("")){
				Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
			}else{
				String str=sp.getString("password","");
				if(MD5.encode(pwd).equals(str)){
					if(!isSetupGuide()){
						setContentView(R.layout.login_dialog);
						tv_protectedNumber=(TextView)findViewById(R.id.tv_lost_protected_number);
						tv_protectedGuide=(TextView)findViewById(R.id.tv_lost_protected_guide);
						cb_isProtected=(CheckBox)findViewById(R.id.cb_lost_protected_isProtected);
						tv_protectedNumber.setText("手机安全号码为:"+sp.getString("number",""));
						tv_protectedGuide.setOnClickListener(this);
						boolean isProtedcting=sp.getBoolean("isProtected",false);
						if(isProtedcting){
							cb_isProtected.setText("已经开启保护");
							cb_isProtected.setChecked(true);
						}
						cb_isProtected.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								if(isChecked){
									cb_isProtected.setText("已近开启保护");
									Editor editor=sp.edit();
									editor.putBoolean("isProtected",true);
									editor.commit();
								}else{
									cb_isProtected.setText("没有开启保护");
									Editor editor=sp.edit();
									editor.putBoolean("isProtected",false);
									editor.commit();
								}
							}
						});
					}
					dialog.dismiss();
				}else{
					Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.bt_protected_login_no:
			dialog.dismiss();
			finish();
			break;
		case R.id.tv_lost_protected_guide://重新进入设置向导
			finish();
			Intent intent=new Intent(this,SetupGuide1Activity.class);
			startActivity(intent);
		default:
			break;
		}
	}

}
