package cn.seu.edu.yuanbaopay.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.gesture.GestureEditActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;





import com.example.yuanbaopay.R;

public class RegisterCreatepwdActivity extends Activity implements
		OnClickListener {

	private TextView tv_title;
	private TextView tvBack;
	private Button btnSet;
	private DialogWidget mDialogWidget;
	private String paypwd;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_createpwd);
		initView();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("注册");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnSet = (Button) findViewById(R.id.btn_nextto_addcard);
		btnSet.setOnClickListener(this);
		pref=PreferenceManager.getDefaultSharedPreferences(RegisterCreatepwdActivity.this);
		editor=pref.edit();
	

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_nextto_addcard:
			mDialogWidget = new DialogWidget(RegisterCreatepwdActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;
		default:
			break;
		}

	}

	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请设置支付密码",
						"",
						"确认",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
							
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								paypwd=password;
								editor.putString("paypwd", paypwd);
								editor.commit();
								AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterCreatepwdActivity.this,R.style.dialog);
								dialog.setMessage("支付密码设置成功");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent i=new Intent(RegisterCreatepwdActivity.this,GestureEditActivity.class);
										startActivity(i);
									}
								});
								dialog.show();
							}

							@Override
							public void onCancelPay() {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "取消设置",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}
}
