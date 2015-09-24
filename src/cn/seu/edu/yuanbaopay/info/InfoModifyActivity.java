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
import android.widget.TextView;

import com.example.yuanbaopay.R;

public class InfoModifyActivity extends Activity implements OnClickListener{
	private TextView tvSave;
	private TextView tvTitle;
	private TextView tvBack;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private EditText edtUserName;
	private EditText edtNickName;
	private EditText edtSex;
	private EditText edtIDNum;
	private EditText edtPhoneNum;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changeinfo);
		initView();
		store();
	}

	private void store() {
		pref=PreferenceManager.getDefaultSharedPreferences(InfoModifyActivity.this);
		editor=pref.edit();
		editor.putString("username", edtUserName.getText().toString());
		editor.putString("nickname", edtNickName.getText().toString());
		editor.putString("sex", edtSex.getText().toString());
		editor.putString("account", edtIDNum.getText().toString());
		editor.putString("phonenum", edtPhoneNum.getText().toString());
		editor.commit();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tvSave = (TextView) findViewById(R.id.tv_save_info);
		tvSave.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("我的资料");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		edtUserName=(EditText)findViewById(R.id.edTxt_name_changeinfo);
		edtNickName=(EditText)findViewById(R.id.edTxt_nickname_changeinfo);
		edtSex=(EditText)findViewById(R.id.edTxt_sex_changeinfo);
		edtIDNum=(EditText)findViewById(R.id.edTxt_idnum_changeinfo);
		edtPhoneNum=(EditText)findViewById(R.id.edTxt_phonenum_changeinfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_save_info:
			Intent i = new Intent(InfoModifyActivity.this, InfoActivity.class);
			startActivity(i);
			break;
		case R.id.title_back:
			finish();
			break;

		default:
			break;

		}
	}
}
