package cn.seu.edu.yuanbaopay.managemoney;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketLuckyActivity;

import com.example.yuanbaopay.R;

public class ManageMoneyInActivity extends Activity implements OnClickListener {
	private TextView tvTitle;
	private TextView tvBack;
	private DialogWidget mDialogWidget;
	private Button btnEnsure;
	private EditText etmoney;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_managemoney_in);
		initView();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("转入到余额宝");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnEnsure = (Button) findViewById(R.id.btn_ensurein_mmin);
		btnEnsure.setOnClickListener(this);
		etmoney=(EditText)findViewById(R.id.edtTxt_moneyin_mmin);
		


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_ensurein_mmin:
			mDialogWidget = new DialogWidget(ManageMoneyInActivity.this,
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
						"请输入支付密码",
						"转入支付宝金额100元",
						"确认转入",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								AlertDialog.Builder dialog = new AlertDialog.Builder(
										ManageMoneyInActivity.this,
										R.style.dialog);
								dialog.setMessage("转入成功");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												pref=PreferenceManager.getDefaultSharedPreferences(ManageMoneyInActivity.this);
												editor=pref.edit();
												editor.putString("mmintype", "转入到余额宝");
												editor.putString("mminmoney", etmoney.getText().toString());
												editor.commit();
												Intent i = new Intent(
														ManageMoneyInActivity.this,
														ManageMoneyActivity.class);
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
								Toast.makeText(getApplicationContext(), "取消转入",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}
}
