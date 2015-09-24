package cn.seu.edu.yuanbaopay.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;

import com.example.yuanbaopay.R;

public class SecurityChangePayPwdActivity extends Activity {
	private TextView tvTitle;
	private TextView tvBack;
	private DialogWidget mDialogWidget;
	private Button btnReset;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_changepaypwd);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("更改支付密码");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnReset=(Button)findViewById(R.id.btn_reset_paypwd);
		btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialogWidget = new DialogWidget(SecurityChangePayPwdActivity.this,
						getDecorViewDialogOld());
				mDialogWidget.show();
			}
		});
		
	}
	protected View getDecorViewDialogOld() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入原支付密码",
						"",
						"确认",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "验证成功",
										Toast.LENGTH_SHORT).show();
								mDialogWidget = new DialogWidget(SecurityChangePayPwdActivity.this,
										getDecorViewDialogNew());
								mDialogWidget.show();
							}

							@Override
							public void onCancelPay() {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "取消更改",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}
	protected View getDecorViewDialogNew() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入新支付密码",
						"",
						"确认",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Intent i = new Intent(
										SecurityChangePayPwdActivity.this,
										SecurityMainActivity.class);
								startActivity(i);
								Toast.makeText(getApplicationContext(), "更改成功",
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onCancelPay() {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								Toast.makeText(getApplicationContext(), "取消更改",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}

}
