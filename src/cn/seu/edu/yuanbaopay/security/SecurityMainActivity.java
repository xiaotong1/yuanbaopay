package cn.seu.edu.yuanbaopay.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.seu.edu.yuanbaopay.gesture.GestureEditActivity;

import com.example.yuanbaopay.R;

public class SecurityMainActivity extends Activity implements OnClickListener{
	private LinearLayout llLoginPwd;
	private LinearLayout llPayPwd;
	private LinearLayout llGesturePwd;
	private LinearLayout llLoss;
	private TextView tvTitle;
	private TextView tvBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_security_main);
		initView();
	}
	private void initView() {
		llLoginPwd=(LinearLayout)findViewById(R.id.layout_changeloginpwd_securitymain);
		llLoginPwd.setOnClickListener(this);
		llPayPwd=(LinearLayout)findViewById(R.id.layout_changepaypwd_securitymain);
		llPayPwd.setOnClickListener(this);
		llGesturePwd=(LinearLayout)findViewById(R.id.layout_changeguesturepwd_securitymain);
		llGesturePwd.setOnClickListener(this);
		llLoss=(LinearLayout)findViewById(R.id.layout_reportloss_securitymain);
		llLoss.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("ÕË»§°²È«");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.layout_changeguesturepwd_securitymain:
			Intent i_guesture=new Intent(SecurityMainActivity.this,GestureEditActivity.class);
			i_guesture.putExtra("activity",true);
			startActivity(i_guesture);
			break;
		case R.id.layout_changeloginpwd_securitymain:
			Intent i_login=new Intent(SecurityMainActivity.this,SecurityChangeLoginPwdActivity.class);
			startActivity(i_login);
			break;
		case R.id.layout_changepaypwd_securitymain:
			Intent i_pay=new Intent(SecurityMainActivity.this,SecurityChangePayPwdActivity.class);
			startActivity(i_pay);
			break;
		case R.id.layout_reportloss_securitymain:
			Intent i_loss=new Intent(SecurityMainActivity.this,SecurityReportLossActivity.class);
			startActivity(i_loss);
			break;
		case R.id.title_back:
			finish();
			break;
		}
	}

}
