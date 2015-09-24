package cn.seu.edu.yuanbaopay.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuanbaopay.R;


public class SecurityChangeLoginPwdActivity extends Activity {
	private TextView tvTitle;
	private TextView tvBack;
	private Button btnNext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_security_changeloginpwd_1);
		initView();
	}
	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("¸ü¸ÄµÇÂ¼ÃÜÂë");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnNext=(Button)findViewById(R.id.btn_next_to_changeloginpwd2);
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(SecurityChangeLoginPwdActivity.this,SecurityChangeLoginPwdSecondActivity.class);
				startActivity(i);
			}
		});
	}

}
