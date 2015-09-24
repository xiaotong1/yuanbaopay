package cn.seu.edu.yuanbaopay.security;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import cn.seu.edu.yuanbaopay.gesture.GestureEditActivity;
import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.main.RegisterCreatepwdActivity;

import com.example.yuanbaopay.R;

public class SecurityChangeLoginPwdSecondActivity extends Activity {
	private TextView tvTitle;
	private TextView tvBack;
	private Button btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_security_changeloginpwd_2);
		initView();
	}

	private void initView() {

		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("更改登录密码");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnNext=(Button)findViewById(R.id.btn_next_to_login1);
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog=new AlertDialog.Builder(SecurityChangeLoginPwdSecondActivity.this,R.style.dialog);
				dialog.setMessage("登陆密码重置成功");
				dialog.setCancelable(false);
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i=new Intent(SecurityChangeLoginPwdSecondActivity.this,SecurityMainActivity.class);
						startActivity(i);
					}
				});
				dialog.show();
			}
		});
	}

}
