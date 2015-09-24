package cn.seu.edu.yuanbaopay.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.gesture.GestureEditActivity;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketLuckyActivity;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketMainActivity;

import com.example.yuanbaopay.R;

public class RegisterActivity extends Activity implements OnClickListener {
	private Button btnRegister;
	private TextView tvTitle;
	private TextView tvBack;
	private EditText edtUsername;
	private EditText edtPassword;
	private EditText edtEnsurePwd;
	private EditText edtPhoneNum;
	private EditText edtIDNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {

		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("注册");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnRegister = (Button) findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(this);
		edtUsername = (EditText) findViewById(R.id.edtTxt_username_register);
		edtPassword = (EditText) findViewById(R.id.edtTxt_password_register);
		edtEnsurePwd = (EditText) findViewById(R.id.edtTxt_password_twice_register);
		edtPhoneNum = (EditText) findViewById(R.id.edtTxt_phonenum_register);
		edtIDNum = (EditText) findViewById(R.id.edtTxt_idnum_register);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			if (edtPassword.getText().equals(edtEnsurePwd.getText())) {
				Intent i = new Intent(RegisterActivity.this,
						GestureEditActivity.class);
				i.putExtra("activity", false);
				startActivity(i);
			} else{
				AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterActivity.this,R.style.dialog);
				dialog.setMessage("两次密码输入不一致！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("确定", null);
				dialog.show();
			}
				break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;

		}
	}

	
}
