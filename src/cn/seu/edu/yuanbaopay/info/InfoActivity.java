package cn.seu.edu.yuanbaopay.info;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.security.SecurityMainActivity;





import com.example.yuanbaopay.R;

public class InfoActivity extends Activity implements OnClickListener {
	private TextView tvExit;
	private TextView tvModify;
	private TextView tvTitle;
	private TextView tvBack;
	private LinearLayout llSecurity;
	private SharedPreferences pref;
	
	private TextView tvUserName;
	private TextView tvNickName;
	private TextView tvSex;
	private TextView tvAccount;
	private TextView tvPhoneNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_info);
		pref=PreferenceManager.getDefaultSharedPreferences(InfoActivity.this);
		initView();
		store();
	}

	private void store() {
		tvUserName.setText(pref.getString("username", ""));
		tvNickName.setText(pref.getString("nickname", ""));
		tvSex.setText(pref.getString("sex", ""));
		tvAccount.setText(pref.getString("account", ""));
		tvPhoneNum.setText(pref.getString("phonenum", ""));
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		tvExit = (TextView) findViewById(R.id.tv_exit_login);
		tvExit.setOnClickListener(this);
		tvModify = (TextView) findViewById(R.id.tv_modify_info);
		tvModify.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("我的资料");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		llSecurity=(LinearLayout)findViewById(R.id.layout_to_security);
		llSecurity.setOnClickListener(this);
		tvUserName=(TextView)findViewById(R.id.tv_name_info);
		tvNickName=(TextView)findViewById(R.id.tv_nickname_info);
		tvSex=(TextView)findViewById(R.id.tv_sex_info);
		tvAccount=(TextView)findViewById(R.id.tv_account_name_info);
		tvPhoneNum=(TextView)findViewById(R.id.tv_phonenum_info);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_exit_login:
			Intent i_exit = new Intent(InfoActivity.this, LoginActivity.class);
			startActivity(i_exit);
			break;
		case R.id.tv_modify_info:
			Intent i_modify = new Intent(InfoActivity.this,
					InfoModifyActivity.class);
			startActivity(i_modify);
			finish();
			break;
		case R.id.title_back:
			finish();
			break;
		case R.id.layout_to_security:
			Intent i_security=new Intent(InfoActivity.this,SecurityMainActivity.class);
			startActivity(i_security);
			break;
		default:
			break;
		}
	}

}
