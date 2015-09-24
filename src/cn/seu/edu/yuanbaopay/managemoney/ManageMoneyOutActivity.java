package cn.seu.edu.yuanbaopay.managemoney;

import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.paypassword.DialogWidget;
import cn.seu.edu.yuanbaopay.paypassword.PayPasswordView;
import cn.seu.edu.yuanbaopay.transfer.TransferLimitActivity;
import cn.seu.edu.yuanbaopay.transfer.TransferToBankActivity;

import com.example.yuanbaopay.R;

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

public class ManageMoneyOutActivity extends Activity implements OnClickListener {
	private TextView tvTitle;
	private TextView tvBack;
	private Button btnEnsure;
	private DialogWidget mDialogWidget;
	private TextView tvLimit;
	private EditText mmout;
	//存储
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mangemoney_out);
		initView();
	}

	private void initView() {
		tvLimit=(TextView)findViewById(R.id.tv_explainlimite_mmout);
		tvLimit.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("从余额宝中转出");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		btnEnsure=(Button)findViewById(R.id.btn_ensure_mmout);
		btnEnsure.setOnClickListener(this);
		mmout=(EditText)findViewById(R.id.edtTxt_moneyout_mmout);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		case R.id.btn_ensure_mmout:
			mDialogWidget = new DialogWidget(ManageMoneyOutActivity.this,
					getDecorViewDialog());
			mDialogWidget.show();
			break;
		case R.id.tv_explainlimite_mmout:
			Intent i=new Intent(ManageMoneyOutActivity.this,TransferLimitActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}
	protected View getDecorViewDialog() {
		// TODO Auto-generated method stub
		return PayPasswordView
				.getInstance(
						"请输入支付密码","从支付宝中转出","确认转出",
						this,
						new cn.seu.edu.yuanbaopay.paypassword.PayPasswordView.OnPayListener() {

							@Override
							public void onSurePay(String password) {
								// TODO Auto-generated method stub
								mDialogWidget.dismiss();
								mDialogWidget = null;
								AlertDialog.Builder dialog = new AlertDialog.Builder(
										ManageMoneyOutActivity.this,
										R.style.dialog);
								dialog.setMessage("转出成功");
								dialog.setCancelable(false);
								dialog.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												pref=PreferenceManager.getDefaultSharedPreferences(ManageMoneyOutActivity.this);
												editor=pref.edit();
														editor.putString("mmouttype", "从余额宝中转出");
														editor.putString("mmoutmoney", mmout.getText().toString());
														editor.commit();
												Intent i = new Intent(
														ManageMoneyOutActivity.this,
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
								Toast.makeText(getApplicationContext(), "取消转出",
										Toast.LENGTH_SHORT).show();

							}
						}).getView();
	}
}
