package com.alan.myguard.ui;

import com.alan.myguard.R;
import com.alan.myguard.adapter.MainUIAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener
{
	private static final String TAG="MyGuard";
	private GridView gridView;
	private MainUIAdapter adapter;
	private SharedPreferences sp; 

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		sp=this.getSharedPreferences("config",Context.MODE_PRIVATE);
		gridView = (GridView) findViewById(R.id.gv_main);
        adapter = new MainUIAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position ==0){
					AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("设置");
					builder.setMessage("请输入姓名");
					final EditText et=new EditText(MainActivity.this);
					et.setHint("新名称");
					builder.setView(et);
					builder.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String name=et.getText().toString();
							if(name.equals("")){
								Toast.makeText(MainActivity.this, "输入内容不能为空",Toast.LENGTH_SHORT).show();
							}else{
								Editor editor=sp.edit();
								editor.putString("lostName",name);
								editor.commit();
								TextView tv=(TextView)findViewById(R.id.tv_main_name);
								tv.setText(name);
								adapter.notifyDataSetChanged();
							}
						}
					});
					
					builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					builder.create().show();
				}
				return false;
			}
		
		});	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch(position){
		case 0: break; //手机防盗
		case 1: break; //通讯卫士
		case 2: break; //软件管理
		case 3: break; //流量管理
		case 4: break; //任务管理
		case 5: break; //手机杀毒
		case 6: break; //系统优化
		case 7: break; //高级工具
		case 8: break; //设置中心
	    default:break;
		}
	}
}
